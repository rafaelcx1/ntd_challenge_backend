package com.ntd.challenge.record.v1.controllers.public_apis.requests;

import lombok.Data;

@Data
public class RecordFilterRequest {

    private Integer operationId;
    private String amount;
    private String userBalance;
    private String operationResponse;
    private String date;
}
