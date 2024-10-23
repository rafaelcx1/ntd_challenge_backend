package com.ntd.challenge.operations.v1.integrations.record_service.models.response;

import lombok.Data;

import java.util.Map;

@Data
public class Record4XXResponse {

    private String title;
    private String detail;
    private Map<String, String> properties;
}
