package com.ntd.challenge.record.v1.controllers.internal;

import com.ntd.challenge.record.v1.controllers.internal.documentation.RecordInternalControllerDocumentation;
import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.services.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/v1/records")
public class RecordInternalController implements RecordInternalControllerDocumentation {

    private final RecordService recordService;

    @Override
    @PostMapping
    public void addOperationToRecord(@RequestBody @Valid AddOperationToRecordRequest request) throws NotEnoughBalanceException {
        recordService.addOperationToRecord(request);
    }
}
