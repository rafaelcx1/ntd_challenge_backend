package com.ntd.challenge.operations.v1.repositories;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByType(OperationTypeEnum type);
}
