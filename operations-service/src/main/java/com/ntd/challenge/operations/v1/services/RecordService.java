package com.ntd.challenge.operations.v1.services;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;

import java.util.UUID;

public interface RecordService {

    void addOperationToRecord(Operation operation, Integer userId, String commandResult) throws NotEnoughBalanceException;
}
