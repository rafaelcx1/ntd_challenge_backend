package com.ntd.challenge.operations.v1.integrations.record_service.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import me.bvn13.openfeign.logger.normalized.NormalizedFeignLogger;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;

public class RecordServiceFeignConfig {
    @Bean
    Logger feignLoggerLevel() {
        return new NormalizedFeignLogger();
    }

    @Bean
    ErrorDecoder errorDecoder() { return new RecordErrorDecoder(); }

    @Bean
    public RequestInterceptor customRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("X-Request-ID", MDC.get("X-Request-ID"));
                requestTemplate.header("X-Correlation-ID", MDC.get("X-Correlation-ID"));
            }
        };
    }

}
