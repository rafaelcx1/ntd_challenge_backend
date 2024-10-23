package com.ntd.challenge.record.v1.services;

import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.entities.Record;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface RecordService {
    void addOperationToRecord(AddOperationToRecordRequest request) throws NotEnoughBalanceException;
    BigDecimal getLoggedUserBalance();

    Page<Record> getRecords(String filter, Pageable pageable);

    void deleteRecord(Integer id) throws RecordNotFoundException;
}
