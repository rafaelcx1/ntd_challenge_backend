package com.ntd.challenge.operations.v1.integrations.record_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntd.challenge.operations.v1.integrations.record_service.exceptions.RecordServiceBadRequestException;
import com.ntd.challenge.operations.v1.integrations.record_service.exceptions.RecordServiceForbiddenException;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.Record4XXResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class RecordErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new RecordServiceBadRequestException(get4XXResponseFromBody(response.body()));
            case 403 -> new RecordServiceForbiddenException(get4XXResponseFromBody(response.body()));
            default -> new Exception("Generic error");
        };
    }

    private Record4XXResponse get4XXResponseFromBody(Response.Body body) {
        try (InputStream bodyIs = body.asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(bodyIs, Record4XXResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
