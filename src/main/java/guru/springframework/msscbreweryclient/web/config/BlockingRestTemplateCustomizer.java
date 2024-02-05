package guru.springframework.msscbreweryclient.web.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
    private final Integer maxTotalConnections;
    private final Integer defaultMaxPerRoute;
    private final Integer connectionRequestTimeout;
    private final Integer socketRequestTimeout;

    public BlockingRestTemplateCustomizer(
            @Value("${sfg.maxTotalConnections:50}") Integer maxTotalConnections,
            @Value("${sfg.defaultMaxPerRoute:10}") Integer defaultMaxPerRoute,
            @Value("${sfg.connectionRequestTimeout:2500}") Integer connectionRequestTimeout,
            @Value("${sfg.socketRequestTimeout:2500}") Integer socketRequestTimeout) {
        this.maxTotalConnections = maxTotalConnections;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketRequestTimeout = socketRequestTimeout;

        log.debug("## ###########################################");
        log.debug("## Blocking REST Template Customizer config");
        log.debug("## ###########################################");
        log.debug("## Max Total Connections: {}", this.maxTotalConnections);
        log.debug("## Default Max per Route: {}", this.defaultMaxPerRoute);
        log.debug("## Connection Request Timeout: {}", this.connectionRequestTimeout);
        log.debug("## Socket Request Timeout: {}", this.socketRequestTimeout);
    }


    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketRequestTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
