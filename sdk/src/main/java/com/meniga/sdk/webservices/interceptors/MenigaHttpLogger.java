package com.meniga.sdk.webservices.interceptors;

import android.util.Log;

import com.meniga.sdk.helpers.LogLevel;
import com.meniga.sdk.helpers.LogType;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * An OkHttp interceptor that logs requests and responses. Supports logging headers and/or body.
 */
public class MenigaHttpLogger implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final String REQUEST_START = " Request Start ----> ";
	private static final String RESPONSE_START = "Response Start ----> ";
	private static final String REQUEST_END = "<---- Request End ";
	private static final String RESPONSE_END = "<---- Response End ";
	private static final String BODY = "BODY:";
	private static final String NO_BODY = "<NO BODY>";

	private String tag = "Meniga";
	private final LogLevel logLevel;
	private final LogType logType;

	public MenigaHttpLogger(String tag, LogLevel logLevel, LogType logType) {
		if(tag != null && tag.length() > 0) {
			this.tag = tag;
		}
		this.logLevel = logLevel;
		this.logType = logType;
	}

	private String getHeaderString(String name, String value) {
		return "HEADER: Key=" + name + ", Value=" + value;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		if (logLevel == LogLevel.NONE) {
			return chain.proceed(request);
		}

		boolean logBody = logType == LogType.BODY_ONLY || logType == LogType.BODY_AND_HEADERS;
		boolean logHeaders = logType == LogType.HEADERS_ONLY || logType == LogType.BODY_AND_HEADERS;

		RequestBody requestBody = request.body();
		boolean hasRequestBody = requestBody != null;
		String reqUrl = request.url().toString();
		String requestStartMessage = request.method() + REQUEST_START + reqUrl;
		if (!logHeaders && hasRequestBody) {
			requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
		}
		log(requestStartMessage);

		if (logHeaders) {
			if (hasRequestBody) {
				// Request body headers are only present when installed as a network interceptor. Force
				// them to be included (when available) so their values are known.
				if (requestBody.contentType() != null) {
					log(getHeaderString("Content-Type", requestBody.contentType().toString()));
				}
				if (requestBody.contentLength() != -1) {
					log(getHeaderString("Content-Length", Long.toString(requestBody.contentLength())));
				}
			}

			Headers headers = request.headers();
			for (int i = 0, count = headers.size(); i < count; i++) {
				String name = headers.name(i);
				// Skip headers from the request body as they are explicitly logged above.
				if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
					log(getHeaderString(name, headers.value(i)));
				}
			}
		}

		if (!logBody) {
			log(REQUEST_END + request.method());
		} else if(!hasRequestBody) {
			log(BODY);
			log(NO_BODY);
		} else if (bodyEncoded(request.headers())) {
			log(REQUEST_END + request.method() + " (encoded body omitted)");
		} else {
			Buffer buffer = new Buffer();
			try {
				requestBody.writeTo(buffer);
			} catch (ConcurrentModificationException ex) {
				log("Error logging body - got ConcurrentModificationException");
			}

			Charset charset = UTF8;
			MediaType contentType = requestBody.contentType();
			if (contentType != null) {
				charset = contentType.charset(UTF8);
			}

			log(BODY);
			if (isPlaintext(buffer)) {
				log(buffer.readString(charset));
				log(REQUEST_END + request.method() + " (" + requestBody.contentLength() + "-byte body)");
			} else {
				log("<" + requestBody.contentLength() + "-BYTE BINARY BODY OMITTED");
				log(REQUEST_END + request.method());
			}
		}

		long startNs = System.nanoTime();
		Response response;
		try {
			response = chain.proceed(request);
		} catch (Exception e) {
			log("<---- HTTP FAILED: " + e);
			throw e;
		}
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

		ResponseBody responseBody = response.body();
		long contentLength = responseBody.contentLength();
		String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
		log(RESPONSE_START + reqUrl);
		log("(" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ")");
		if (logHeaders) {
			Headers headers = response.headers();
			for (int i = 0, count = headers.size(); i < count; i++) {
				log(getHeaderString(headers.name(i), headers.value(i)));
			}
		}

		if (!logBody) {
			log(RESPONSE_END);
		} else if(!HttpHeaders.hasBody(response)) {
			log(BODY);
			log(NO_BODY);
			log(RESPONSE_END);
		} else if (bodyEncoded(response.headers())) {
			log(BODY);
			log("<ENCODED BODY OMITTED>");
			log(RESPONSE_END);
		} else {
			BufferedSource source = responseBody.source();
			source.request(Long.MAX_VALUE); // Buffer the entire body.
			Buffer buffer = source.buffer();

			Charset charset = UTF8;
			MediaType contentType = responseBody.contentType();
			if (contentType != null) {
				try {
					charset = contentType.charset(UTF8);
				} catch (UnsupportedCharsetException e) {
					log(BODY);
					log("<ERROR DECODING BODY; CHARSET IS LIKELY MALFORMED.>");
					log(RESPONSE_END);

					return response;
				}
			}

			if (!isPlaintext(buffer)) {
				log(BODY);
				log("<BINARY " + buffer.size() + "-BYTE BODY OMITTED>");
				log(RESPONSE_END);
				return response;
			}

			if (contentLength != 0) {
				log(BODY + " (" + buffer.size() + "-byte body)");
				log(buffer.clone().readString(charset));
			}

			log(RESPONSE_END);
		}

		return response;
	}

	private boolean bodyEncoded(Headers headers) {
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
	}

	static boolean isPlaintext(Buffer buffer) throws EOFException {
			Buffer prefix = new Buffer();
			long byteCount = buffer.size() < 64 ? buffer.size() : 64;
			buffer.copyTo(prefix, 0, byteCount);
			for (int i = 0; i < 16; i++) {
				if (prefix.exhausted()) {
					break;
				}
				if (Character.isISOControl(prefix.readUtf8CodePoint())) {
					return false;
				}
			}
			return true;
	}

	private void log(String msg) {
		if (msg == null) {
			return;
		}
		switch (logLevel) {
			case DEBUG:
				Log.d(tag, msg);
				break;
			case NONE:
				return;
			case WARN:
				Log.w(tag, msg);
				break;
			case ERROR:
				Log.e(tag, msg);
				break;
			case INFO:
				Log.i(tag, msg);
				break;
			case VERBOSE:
			default:
				Log.v(tag, msg);
				break;
		}
	}
}
