package com.ntd.challenge.record.v1.integrations.operations_service;


import com.ntd.challenge.record.v1.integrations.operations_service.config.OperationsServiceFeignConfig;
import com.ntd.challenge.record.v1.integrations.operations_service.models.response.OperationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "operations-service", url = "${integrations.operations-service.base-url}", configuration = OperationsServiceFeignConfig.class)
public interface OperationsServiceIntegration {


    @GetMapping("operations")
    List<OperationResponse> getAllOperations();
}
