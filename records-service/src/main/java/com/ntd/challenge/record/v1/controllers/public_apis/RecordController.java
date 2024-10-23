package com.ntd.challenge.record.v1.controllers.public_apis;

import com.ntd.challenge.record.v1.controllers.public_apis.documentation.RecordControllerDocumentation;
import com.ntd.challenge.record.v1.controllers.public_apis.responses.RecordResponse;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
import com.ntd.challenge.record.v1.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/records")
public class RecordController implements RecordControllerDocumentation {

    private final ModelMapper mapper;
    private final RecordService recordService;

    @Override
    @GetMapping
    public PagedModel<RecordResponse> getRecords(@RequestParam String filter, Pageable pageable) {
        Page<RecordResponse> records = recordService.getRecords(filter, pageable)
                .map(r -> mapper.map(r, RecordResponse.class));

        return new PagedModel<>(records);
    }

    @Override
    @DeleteMapping("{id}")
    public void deleteRecord(@PathVariable Integer id) throws RecordNotFoundException {
        recordService.deleteRecord(id);
    }

    @Override
    @GetMapping("/balance")
    public BigDecimal getLoggedUserBalance() {
        return recordService.getLoggedUserBalance();
    }

}
