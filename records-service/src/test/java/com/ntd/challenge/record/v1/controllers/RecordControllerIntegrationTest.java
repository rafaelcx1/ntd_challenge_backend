package com.ntd.challenge.record.v1.controllers;

import com.ntd.challenge.record.v1.configurations.WalletProperties;
import com.ntd.challenge.record.v1.entities.Record;
import com.ntd.challenge.record.v1.integrations.operations_service.OperationsServiceIntegration;
import com.ntd.challenge.record.v1.repositories.RecordRepository;
import com.ntd.challenge.record.v1.security.utils.AuthContextUtils;
import com.ntd.challenge.record.v1.utils.AbstractIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithUserDetails
public class RecordControllerIntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private WalletProperties walletProperties;

    @MockBean
    private OperationsServiceIntegration operationsServiceIntegration;

    @Autowired
    private RecordRepository recordRepository;

    private static MockedStatic<AuthContextUtils> authContextUtilsMockedStatic;

    @BeforeEach
    public void setup() {
        when(walletProperties.getInitialBalance()).thenReturn(new BigDecimal(100));
        authContextUtilsMockedStatic = mockStatic(AuthContextUtils.class);
        authContextUtilsMockedStatic.when(AuthContextUtils::getLoggedUserId).thenReturn(1);
    }

    @AfterEach
    public void teardown() {
        recordRepository.deleteAll();
        authContextUtilsMockedStatic.clearInvocations();
        authContextUtilsMockedStatic.close();
    }

    @Test
    void getRecords_ShouldReturnPagedRecords() throws Exception {
        List<Record> records = List.of(
                mockRecord(new BigDecimal(90), new BigDecimal(10)),
                mockRecord(new BigDecimal(80), new BigDecimal(10)),
                mockRecord(new BigDecimal(70), new BigDecimal(10)),
                mockRecord(new BigDecimal(60), new BigDecimal(10)),
                mockRecord(new BigDecimal(50), new BigDecimal(10)),
                mockRecord(new BigDecimal(40), new BigDecimal(10)),
                mockRecord(new BigDecimal(30), new BigDecimal(10)),
                mockRecord(new BigDecimal(20), new BigDecimal(10)),
                mockRecord(new BigDecimal(10), new BigDecimal(10)),
                mockRecord( new BigDecimal(0), new BigDecimal(10))
        );

        recordRepository.saveAll(records);

        // When & Then
        mockMvc.perform(get("/v1/records")
                        .param("page", "0")
                        .param("size", "5")
                        .param("filter", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.page.totalPages").value(2))
                .andExpect(jsonPath("$.page.totalElements").value(10))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(5));
    }

    @Test
    void deleteRecord_ShouldDeleteRecordSuccessfully() throws Exception {
        // Given
        Record saved = recordRepository.save(mockRecord(new BigDecimal(0), new BigDecimal(10)));

        // When & Then
        mockMvc.perform(delete("/v1/records/{id}", saved.getId()))
                .andExpect(status().isOk());

        Optional<Record> record = recordRepository.findById(saved.getId());
        assertTrue(record.isPresent());
        assertTrue(record.get().isDeleted());
    }

    @Test
    void deleteRecord_ShouldReturnNotFound_WhenRecordDoesNotExist() throws Exception {
        // Given
        Integer recordId = 3000;

        // When & Then
        mockMvc.perform(delete("/v1/records/{id}", recordId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLoggedUserBalance_ShouldReturnBalance1() throws Exception {
        List<Record> records = List.of(
                mockRecord(new BigDecimal(90), new BigDecimal(10)),
                mockRecord(new BigDecimal(80), new BigDecimal(10)),
                mockRecord(new BigDecimal(70), new BigDecimal(10)),
                mockRecord(new BigDecimal(60), new BigDecimal(10)),
                mockRecord(new BigDecimal(50), new BigDecimal(10)),
                mockRecord(new BigDecimal(40), new BigDecimal(10)),
                mockRecord(new BigDecimal(30), new BigDecimal(10)),
                mockRecord(new BigDecimal(20), new BigDecimal(10)),
                mockRecord(new BigDecimal(10), new BigDecimal(10)),
                mockRecord( new BigDecimal(0), new BigDecimal(10))
        );

        recordRepository.saveAll(records);

        // When & Then
        mockMvc.perform(get("/v1/records/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0.00"));
    }

    @Test
    void getLoggedUserBalance_ShouldReturnBalance2() throws Exception {
        List<Record> records = List.of(
                mockRecord(new BigDecimal(90), new BigDecimal(10)),
                mockRecord(new BigDecimal(80), new BigDecimal(10)),
                mockRecord(new BigDecimal(70), new BigDecimal(10)),
                mockRecord(new BigDecimal(60), new BigDecimal(10)),
                mockRecord(new BigDecimal(50), new BigDecimal(10)),
                mockRecord(new BigDecimal(40), new BigDecimal(10))
        );

        recordRepository.saveAll(records);

        // When & Then
        mockMvc.perform(get("/v1/records/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("40.00"));
    }

    private Record mockRecord(BigDecimal balance, BigDecimal amount) {
        return Record.builder()
                .operationId(1)
                .userId(1)
                .userBalance(balance)
                .date(Instant.now())
                .operationResponse("1")
                .amount(amount)
                .build();
    }
}

