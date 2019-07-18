package com.meniga.sdk;

import com.meniga.sdk.adapters.TaskAdapter;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.eventconverters.generic.MenigaAccountAvailableAmountEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaChallengeEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaCustomEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaDialogEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaTransactionCountEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaTransactionEventConverter;
import com.meniga.sdk.interfaces.PersistenceMode;
import com.meniga.sdk.interfaces.PersistenceProvider;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.providers.BasicTaskAdapter;
import com.meniga.sdk.webservices.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaSettings {

	private final HttpUrl endpoint;
	private final Authenticator authenticator;
	private final long timeoutInSeconds;
	private final PersistenceMode persistenceMode;
	private PersistenceProvider persistenceProvider;
	private final Map<Service, SpecialServiceEndpointDefinition> specialServiceEndpoints;
	private final List<Interceptor> interceptors;
	private final List<Interceptor> networkInterceptors;
	private final CertificatePinner certificatePinner;
	private final List<EventBaseConverter> userEventFeedConverters;
	private final CustomErrorHandler errorHandler;
	private final TaskAdapter taskAdapter;
	private String culture;
	private SSLSocketFactory sslSocketFactory;
	private X509TrustManager x509TrustManager;

	private MenigaSettings(Builder builder) {
		endpoint = builder.endpoint;
		timeoutInSeconds = builder.timeoutInSeconds <= 0 ? 60 : builder.timeoutInSeconds;
		authenticator = builder.authenticator;
		persistenceProvider = builder.persistenceProvider;
		persistenceMode = builder.persistenceMode;
		specialServiceEndpoints = builder.specialServiceEndpoints;
		interceptors = builder.interceptors;
		userEventFeedConverters = builder.userEventFeedConverters;
		taskAdapter = builder.taskAdapter;
		errorHandler = builder.errorHandler;
		networkInterceptors = builder.networkInterceptors;
		certificatePinner = builder.certificatePinner;
		culture = builder.culture;
		sslSocketFactory = builder.sslSocketFactory;
		x509TrustManager = builder.x509TrustManager;
	}

	/**
	 * @return Returns the framework culture being used (e.g. is-IS, en-GB etc)
	 */
	public String getCulture() {
		return culture;
	}

	/**
	 * Only setter for the settings because culture can be updated after building the initial settings.
	 *
	 * @param culture The new culture to use, in the form e.g. is-IS, en-GB etc.
	 */
	public void updateCulture(String culture) {
		this.culture = culture;
	}

	/**
	 * Returns the authentication provider implementation used by the SDK.
	 *
	 * @return Authentication Provider implementation used by the SDK.
	 */
	public Authenticator getAuthenticator() {
		return authenticator;
	}

	/**
	 * Returns the current endpoint used by the SDK.
	 *
	 * @return current endpoint used by the SDK.
	 */
	public HttpUrl getEndpoint() {
		return endpoint;
	}

	/**
	 * Returns x509TrustManager for custom certificates;
	 *
	 * @return x509TrustManager
	 */
	public X509TrustManager getX509TrustManager() {
		return x509TrustManager;
	}

	/**
	 * Returns SSLSocketFactory for custom certificates;
	 *
	 * @return SSLSocketFactory
	 */
	public SSLSocketFactory getSslSocketFactory() {
		return sslSocketFactory;
	}

	/**
	 * Returns the current error handler used by the SDK.
	 *
	 * @return current error handler used by the SDK.
	 */
	public CustomErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/**
	 * Returns a map containing endpoints for specific service endpoints, e.g. if a
	 * certain feature needs to communicate with a different server than the rest of the modules
	 *
	 * @return The map containing the mapping between model types and their endpoints
	 */
	public Map<Service, SpecialServiceEndpointDefinition> getSpecialServiceEndpoints() {
		return specialServiceEndpoints;
	}

	/**
	 * Gets the OkHttp read and write timeoutInSeconds limit in ms
	 *
	 * @return The read and write timout in ms
	 */
	public long getTimeoutInSeconds() {
		return timeoutInSeconds;
	}

	/**
	 * @return Returns the persistence provider object.
	 */
	public PersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * @return Get all the custom interceptors that have been added to the OkHttp client by the user
	 */
	public List<Interceptor> getHttpInterceptors() {
		return interceptors;
	}

	/**
	 * @return Get all the custom Network interceptors that have been added to the OkHttp client by the user
	 */
	public List<Interceptor> getNetworkInterceptors() {
		return networkInterceptors;
	}

	@Nullable
	public CertificatePinner getCertificatePinner() {
		return certificatePinner;
	}

	/**
	 * Returns all user event feed converters in use.
	 *
	 * @return List of all UserEventConverters.
	 */
	public List<EventBaseConverter> getUserEventFeedConverters() {
		return userEventFeedConverters;
	}

	public TaskAdapter getTaskAdapter() {
		return taskAdapter;
	}

	public static class Builder {

		private HttpUrl endpoint;
		private Authenticator authenticator;
		private PersistenceMode persistenceMode;
		private long timeoutInSeconds;
		private PersistenceProvider persistenceProvider;
		private Map<Service, SpecialServiceEndpointDefinition> specialServiceEndpoints = new HashMap<>();
		private List<Interceptor> interceptors = new ArrayList<>();
		private List<Interceptor> networkInterceptors = new ArrayList<>();
		private CertificatePinner certificatePinner;
		private final List<EventBaseConverter> userEventFeedConverters = new ArrayList<>();
		private CustomErrorHandler errorHandler;
		private TaskAdapter taskAdapter;
		private String culture = "en-GB";
		private SSLSocketFactory sslSocketFactory;
		private X509TrustManager x509TrustManager;

		/**
		 * Sets a endpoint URL for the SDK to communicate with.
		 *
		 * @param endpoint an HttpUrl object with an parsed endpoint URL.
		 * @return Builder object.
		 */
		public Builder endpoint(HttpUrl endpoint) {
			this.endpoint = endpoint;
			if (!endpoint.toString().endsWith("/")) {
				this.endpoint = HttpUrl.parse(endpoint.toString() + "/");
			}
			return this;
		}

		/**
		 * Sets a endpoint URL for the SDK to communicate with.
		 *
		 * @param endpoint an String object with an endpoint URL.
		 * @return Builder object.
		 */
		public Builder endpoint(String endpoint) {
			if (!endpoint.endsWith("/")) {
				endpoint = endpoint + "/";
			}
			this.endpoint = HttpUrl.parse(endpoint);
			return this;
		}

		/**
		 * Sets a custom authenticator. Consumers have to implement Authenticator interface provided with OkHttp and provide a way for the SDK to authenticate
		 * itself to an endpoint. This varies between consumers who often have custom authentication schemes.
		 *
		 * @param authenticator An implementation of AuthenticationProvider interface.
		 * @return Builder object.
		 */
		public Builder authenticator(Authenticator authenticator) {
			this.authenticator = authenticator;
			return this;
		}

		/**
		 * Sets a custom error handler. If set the error handler will be called to handle all errors
		 * occurring inside the sdk. The default behavior is to log out the throwable at level error via
		 * the standard android logging functionality.
		 *
		 * @param errorHandler An implementation of CustomErrorHandler interface.
		 * @return Builder object.
		 */
		public Builder errorHandler(CustomErrorHandler errorHandler) {
			this.errorHandler = errorHandler;
			return this;
		}

		/**
		 * Sets the concrete persistence implementation to be used in the Meniga SDK
		 *
		 * @param persistenceProvider The persistance provider, implementing the PersistenceProvider interface
		 * @return Builder object
		 *
		 * @deprecated Use {@link #persistenceMode(PersistenceMode)} instead.
		 */
		@Deprecated
		public Builder persistanceProvider(PersistenceProvider persistenceProvider) {
			return persistenceProvider(persistenceProvider);
		}

		/**
		 * Sets the concrete persistence implementation to be used in the Meniga SDK
		 *
		 * @param persistenceProvider The persistence provider, implementing the PersistenceProvider interface
		 * @return Builder object
		 */
		public Builder persistenceProvider(@Nullable PersistenceProvider persistenceProvider) {
			this.persistenceProvider = persistenceProvider;
			return this;
		}

		/**
		 * Sets the persistence mode. There are two modes, persistence first and network first. Persistence
		 * first always first checks to see if an object can be found in the persistence store and then
		 * goes on the network if it is not found, network first always first goes through the network and
		 * only checks and retrieved a persisted object if a network call fails
		 *
		 * @param persistenceMode The persistance mode enum
		 * @return Builder object
		 */
		public Builder persistenceMode(PersistenceMode persistenceMode) {
			this.persistenceMode = persistenceMode;
			return this;
		}

		/**
		 * Sets the OkHttp read and write timeoutInSeconds limit in ms
		 *
		 * @param timeout The time out in ms
		 * @return Builder object
		 */
		public Builder timeout(long timeout) {
			this.timeoutInSeconds = timeout;
			return this;
		}

		/**
		 * Adds a special endpoint url for a specific model class type (service). This way certain
		 * model classes can use other endpoints than the default given one.
		 *
		 * @param service  The service should have a different endpoint
		 * @param endpoint The endpoint for the model class type
		 * @return Returns settings builder
		 */
		public Builder addEndpointForService(Service service, String endpoint) {
			return addEndpointForServiceWithTimeout(service, endpoint, 0);
		}

		/**
		 * Adds a special endpoint url for a specific model class type (service). This way certain
		 * model classes can use other endpoints than the default given one. Additionally
		 * specifies the timeoutInSeconds the client should use for the service.
		 *
		 * @param service The service should have a different endpoint
		 * @param endpoint The endpoint for the model class type
		 * @param timeoutInSeconds The client timeoutInSeconds, in seconds
		 * @return Returns settings builder
		 */
		public Builder addEndpointForServiceWithTimeout(Service service, String endpoint, int timeoutInSeconds) {
			if (specialServiceEndpoints == null) {
				specialServiceEndpoints = new HashMap<>();
			}
			if (!endpoint.endsWith("/")) {
				endpoint = endpoint + "/";
			}
			specialServiceEndpoints.put(service, new SpecialServiceEndpointDefinition(endpoint, timeoutInSeconds));
			return this;
		}

		/**
		 * Adds an interceptor to the OkHttp client for custom request/response intercept operations as needed
		 *
		 * @param interceptor The OkHttp interceptor to createOfferAccount
		 * @return Returns settings builder
		 */
		public Builder addHttpInterceptor(Interceptor interceptor) {
			interceptors.add(interceptor);
			return this;
		}

		/**
		 * Adds an network interceptor to the OkHttp client
		 *
		 * @param interceptor The OkHttp network interceptor
		 * @return Returns settings builder
		 */
		public Builder addNetworkInterceptor(Interceptor interceptor) {
			networkInterceptors.add(interceptor);
			return this;
		}

		public Builder certificatePinner(@Nullable CertificatePinner certificatePinner) {
			this.certificatePinner = certificatePinner;
			return this;
		}

		public Builder useTaskAdapter(TaskAdapter taskAdapter) {
			this.taskAdapter = taskAdapter;
			return this;
		}

		/**
		 * Sets OkHTTP sslSocketFactory for use with custom certificates;
		 *
		 * @param sslSocketFactory custom sslSocketFactory instance
		 * @param x509TrustManager custom x509TrustManager instance
		 * @return Returns settings builder
		 */
		public Builder useSSLFactory(SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
			this.sslSocketFactory = sslSocketFactory;
			this.x509TrustManager = x509TrustManager;
			return this;
		}

		/**
		 * Sets the framework culture, e.g. en-GB, is-IS etc.
		 *
		 * @param culture The culture in xx-xx form
		 * @return Returns settings builder
		 */
		public Builder culture(String culture) {
			this.culture = culture;
			return this;
		}

		/**
		 * Adds an User event converter for the MenigaFeed object. Otherwise it will use provided default providers or return a generic userevent object.
		 *
		 * @param converter An implementation of UserEventConverter
		 * @return Returns settings builder
		 */
		public Builder addUserEventFeedConverter(EventBaseConverter<MenigaFeedItem> converter) {
			userEventFeedConverters.add(converter);
			return this;
		}

		/**
		 * Builds the MenigaServerSettings object.
		 *
		 * @return Completed MenigaServerSettings object.
		 */
		public MenigaSettings build() {
			if (taskAdapter == null) {
				taskAdapter = new BasicTaskAdapter();
			}
			preBuild();
			return new MenigaSettings(this);
		}

		private void preBuild() {
			// Add default event converters
			userEventFeedConverters.add(new MenigaTransactionEventConverter());
			userEventFeedConverters.add(new MenigaAccountAvailableAmountEventConverter());
			userEventFeedConverters.add(new MenigaCustomEventConverter());
			userEventFeedConverters.add(new MenigaTransactionCountEventConverter());
			userEventFeedConverters.add(new MenigaDialogEventConverter());
			userEventFeedConverters.add(new MenigaChallengeEventConverter());
		}
	}
}
