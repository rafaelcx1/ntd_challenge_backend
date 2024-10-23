package com.ntd.challenge.record.v1.controllers.public_apis.documentation;

import com.ntd.challenge.record.v1.controllers.public_apis.responses.RecordResponse;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.math.BigDecimal;

@Tag(name = "Record")
public interface RecordControllerDocumentation {

    @Operation(summary = "Get Records")
    PagedModel<RecordResponse> getRecords(String filter, Pageable pageable);

    @Operation(summary = "Delete Record")
    void deleteRecord(Integer id) throws RecordNotFoundException;

    @Operation(summary = "Get User Balance")
    BigDecimal getLoggedUserBalance();
}
