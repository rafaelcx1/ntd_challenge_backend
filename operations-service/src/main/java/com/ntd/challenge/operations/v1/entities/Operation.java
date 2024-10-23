package com.ntd.challenge.operations.v1.entities;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "operations")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    private OperationTypeEnum type;

    private BigDecimal cost;
}
