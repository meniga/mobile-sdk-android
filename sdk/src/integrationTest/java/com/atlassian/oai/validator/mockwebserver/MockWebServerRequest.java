package com.atlassian.oai.validator.mockwebserver;

import com.atlassian.oai.validator.model.Request;
import com.meniga.sdk.helpers.Objects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nonnull;

import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

class MockWebServerRequest implements Request {

    private final RecordedRequest request;

    MockWebServerRequest(RecordedRequest request) {
        this.request = Objects.requireNonNull(request);
    }

    @Nonnull
    @Override
    public String getPath() {
        return request.getRequestUrl().encodedPath();
    }

    @Nonnull
    @Override
    public Method getMethod() {
        return Request.Method.valueOf(request.getMethod());
    }

    @Nonnull
    @Override
    public Optional<String> getBody() {
        return Optional.ofNullable(request.getBody())
                .map(new Function<Buffer, String>() {
                    @Override
                    public String apply(Buffer buffer) {
                        return buffer.snapshot().utf8();
                    }
                });
    }

    @Nonnull
    @Override
    public Collection<String> getQueryParameters() {
        return request.getRequestUrl().queryParameterNames();
    }

    @Nonnull
    @Override
    public Collection<String> getQueryParameterValues(final String name) {
        return request.getRequestUrl().queryParameterValues(name);
    }

    @Nonnull
    @Override
    public Map<String, Collection<String>> getHeaders() {
        Map<String, Collection<String>> map = new HashMap<>();
        map.putAll(request.getHeaders().toMultimap());
        return map;
    }

    @Nonnull
    @Override
    public Collection<String> getHeaderValues(String name) {
        return request.getHeaders().values(name);
    }
}
