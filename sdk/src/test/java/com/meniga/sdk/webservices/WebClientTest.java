package com.meniga.sdk.webservices;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.providers.BasicAuthenticator;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class WebClientTest {

	@Test(expected = NullPointerException.class)
	public void testWebClientFailureNotInitialized() {
		MenigaSDK.init(null);
	}

	@Test
	public void testClient() {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.build();
		MenigaSDK.init(settings);
		assertThat(MenigaSDK.executor().getApis())
				.containsKey(Service.ALL)
				.containsKey(Service.BUDGET);
	}

	@Test
	public void testClients() {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.addEndpointForService(Service.TRANSACTIONS, "http://example.transactions.com")
				.build();
		MenigaSDK.init(settings);
		assertThat(MenigaSDK.executor().getApis())
				.containsKey(Service.ALL)
				.containsKey(Service.BUDGET)
				.containsKey(Service.TRANSACTIONS);
	}

	@Test
	public void testSpecialEndpointsAndTimeout() throws IllegalAccessException {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.timeout(20)
				.addEndpointForService(Service.TRANSACTIONS, "http://example.transactions.com")
				.addEndpointForServiceWithTimeout(Service.ACCOUNTS, "http://example.accounts.com", 100)
				.build();
		MenigaSDK.init(settings);
		Map<Service, ?> services = MenigaSDK.executor().getApis();
		assertThat(MenigaSDK.executor().getApis()).containsKey(Service.TRANSACTIONS);
		assertThat(MenigaSDK.executor().getApis()).containsKey(Service.ACCOUNTS);

		OkHttpClient client = getOktHttpClient(services, Service.TRANSACTIONS);
		Retrofit retrofit = getRetrofit(services, Service.TRANSACTIONS);
		assertThat(retrofit).isNotNull();
		assertThat(retrofit.baseUrl().toString()).isEqualTo("http://example.transactions.com/");
		assertThat(client).isNotNull();
		assertThat(client.writeTimeoutMillis()).isEqualTo(20000);

		client = getOktHttpClient(services, Service.ACCOUNTS);
		retrofit = getRetrofit(services, Service.ACCOUNTS);
		assertThat(retrofit).isNotNull();
		assertThat(retrofit.baseUrl().toString()).isEqualTo("http://example.accounts.com/");
		assertThat(client).isNotNull();
		assertThat(client.writeTimeoutMillis()).isEqualTo(100000);
	}

	private Retrofit getRetrofit(Map<Service, ?> services, Service service) throws IllegalAccessException {
		InvocationHandler handler = Proxy.getInvocationHandler(services.get(service));
		Field[] fields = handler.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(handler);
			if (value instanceof Retrofit) {
				return (Retrofit) value;
			}
		}
		return null;
	}

	private OkHttpClient getOktHttpClient(Map<Service, ?> services, Service service) throws IllegalAccessException {
		Retrofit retrofit = getRetrofit(services, service);
		if (retrofit != null && retrofit.callFactory() instanceof OkHttpClient) {
			return (OkHttpClient) retrofit.callFactory();
		}
		return null;
	}
}
