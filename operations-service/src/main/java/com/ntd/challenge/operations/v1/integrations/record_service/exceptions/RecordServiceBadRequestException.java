package com.ntd.challenge.operations.v1.integrations.record_service.exceptions;

import com.ntd.challenge.operations.v1.integrations.record_service.models.response.Record4XXResponse;
import lombok.Getter;


@Getter
public class RecordServiceBadRequestException extends RuntimeException {

    private final Record4XXResponse response;

    public RecordServiceBadRequestException(Record4XXResponse response) {
        super(response == null ? "" : response.getDetail());
        this.response = response;
    }
}
