package com.ntd.challenge.record.v1.repositories;

import com.ntd.challenge.record.v1.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer>, JpaSpecificationExecutor<Record> {

    @Query("SELECT r.userBalance FROM Record r WHERE r.userId = :userId AND r.isDeleted IS FALSE ORDER BY r.id DESC LIMIT 1")
    Optional<BigDecimal> findUserBalanceFromLastRecord(Integer userId);

    Optional<Record> findByIdAndUserIdAndIsDeletedFalse(Integer id, Integer userId);

    @Modifying
    @Query("UPDATE Record r SET r.userBalance = r.userBalance + :amount WHERE r.id > :lastId AND r.isDeleted IS FALSE AND r.userId = :userId")
    void updateUserBalanceInRecordsAfter(Integer userId, Integer lastId, BigDecimal amount);
}
