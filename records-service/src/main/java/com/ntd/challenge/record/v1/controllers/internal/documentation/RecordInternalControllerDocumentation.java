package com.ntd.challenge.record.v1.controllers.internal.documentation;

import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Record Internal")
public interface RecordInternalControllerDocumentation {

    @Operation(summary = "Add operation to records")
    void addOperationToRecord(AddOperationToRecordRequest request) throws NotEnoughBalanceException;
}
