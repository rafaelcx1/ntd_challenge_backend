package com.ntd.challenge.operations.v1.integrations.record_service;


import com.ntd.challenge.operations.v1.integrations.record_service.config.RecordServiceFeignConfig;
import com.ntd.challenge.operations.v1.integrations.record_service.models.request.AddOperationToRecordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "record-service", url = "${integrations.records-service.base-url}", configuration = RecordServiceFeignConfig.class)
public interface RecordServiceIntegration {


    @PostMapping("records")
    void addOperationToRecord(@RequestBody AddOperationToRecordRequest request);
}
