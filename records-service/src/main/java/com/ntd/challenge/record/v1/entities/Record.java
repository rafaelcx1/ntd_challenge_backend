package com.ntd.challenge.record.v1.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "records")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer operationId;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal userBalance;

    private String operationResponse;

    private Instant date;

    private boolean isDeleted;
}
