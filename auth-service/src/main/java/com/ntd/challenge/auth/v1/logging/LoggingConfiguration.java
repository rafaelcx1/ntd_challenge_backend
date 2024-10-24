package com.ntd.challenge.auth.v1.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "logging.loki")
public class LoggingConfiguration {
    private static final String LOKI_APPENDER_NAME = "LOKI";

    private String requestIdHeaderName = "X-Request-ID";
    private String correlationIdHeaderName = "X-Correlation-ID";

    @Bean
    public UniqueIDGenerator generator() {
        return new UniqueIDGenerator(requestIdHeaderName, correlationIdHeaderName);
    }

    @Bean
    public SpringLoggingFilter loggingFilter() {
        return new SpringLoggingFilter(generator(), null, true);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnBean
    public RestTemplate existingRestTemplate(final RestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorList.add(new RestTemplateSetHeaderInterceptor());
        restTemplate.setInterceptors(interceptorList);
        return restTemplate;
    }
}
