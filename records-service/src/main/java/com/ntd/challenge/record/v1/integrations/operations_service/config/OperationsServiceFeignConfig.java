package com.ntd.challenge.record.v1.integrations.operations_service.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import me.bvn13.openfeign.logger.normalized.NormalizedFeignLogger;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;

public class OperationsServiceFeignConfig {
    @Bean
    Logger feignLoggerLevel() {
        return new NormalizedFeignLogger();
    }

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
