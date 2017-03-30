package com.kt.giga.home.openapi.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientPoolConfig {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${rest.client.connectionTimeoutMillis}")
	private int restClientConnectionTimeoutMilli;

	@Value("${rest.client.readTimeoutMillis}")
	private int restClientReadTimeoutMillis;

	@Value("${rest.client.maxConnectionPerHost}")
	private int restClientMaxConnectionsPerHost;

	@Value("${rest.client.maxTotalConnections}")
	private int restClientMaxTotalConnections;

	@Value("${rest.client.defaultKeepAliveMillis}")
	private int restClientKeepAliveMillis;

	@Bean
	public CloseableHttpClient getHttpClient() {
		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

		connectionManager.setDefaultMaxPerRoute(restClientMaxConnectionsPerHost);
		connectionManager.setMaxTotal(restClientMaxTotalConnections);
//		connectionManager.setSocketConfig(host, socketConfig);

//		HttpRequestInterceptor interceptor = new HttpRequestInterceptor() {
//
//			@Override
//			public void process(HttpRequest req, HttpContext ctx) throws HttpException, IOException {
//				log.debug(req.toString() + " http request fired.");
//			}
//		};

		ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {

			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));

				while (it.hasNext()) {
					HeaderElement header = it.nextElement();
					String param = header.getName();
					String value = header.getValue();

					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return restClientKeepAliveMillis;
			}
		};



		final CloseableHttpClient client = HttpClientBuilder
				.create()
				.setKeepAliveStrategy(keepAliveStrategy)
				.setConnectionManager(connectionManager)
				.disableAuthCaching()
//				.addInterceptorFirst(interceptor)
				.build();

		return client;
	}

	@Bean
	public ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(getHttpClient());
//		factory.setConnectTimeout(restClientConnectionTimeoutMilli);
//		factory.setReadTimeout(restClientReadTimeoutMillis);

		return factory;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		return restTemplate;
	}


}
