package com.ntd.challenge.record.v1.controllers.internal;

import com.ntd.challenge.record.v1.configurations.WalletProperties;
import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.repositories.RecordRepository;
import com.ntd.challenge.record.v1.utils.AbstractIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails
public class RecordInternalControllerIntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private WalletProperties walletProperties;

    @Autowired
    private RecordRepository recordRepository;

    @AfterEach
    public void destroy() {
        recordRepository.deleteAll();
    }

    @Test
    void addOperationToRecord_ShouldAddRecordSuccessfully() throws Exception {
        // Given
        AddOperationToRecordRequest request = new AddOperationToRecordRequest();
        request.setUserId(1);
        request.setCost(new BigDecimal("10.00"));
        request.setOperationId(1);
        request.setResult("1");
        request.setType(OperationTypeEnum.ADDITION);

        when(walletProperties.getInitialBalance()).thenReturn(new BigDecimal("100"));

        // When
        mockMvc.perform(post("/internal/v1/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Then
        Optional<BigDecimal> userBalance = recordRepository.findUserBalanceFromLastRecord(1);
        assertTrue(userBalance.isPresent());
        assertEquals(0, userBalance.get().compareTo(new BigDecimal("90")));
    }

    @Test
    void addOperationToRecord_ShouldReturnBadRequest_WhenBalanceIsInsufficient() throws Exception {
        // Given
        AddOperationToRecordRequest request = new AddOperationToRecordRequest();
        request.setUserId(1);
        request.setCost(new BigDecimal("10.00"));
        request.setOperationId(1);
        request.setResult("1");
        request.setType(OperationTypeEnum.ADDITION);

        when(walletProperties.getInitialBalance()).thenReturn(BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/internal/v1/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.title").value(NotEnoughBalanceException.CODE));
    }

    @Test
    void addOperationToRecord_ShouldReturnBadRequest_WhenRequestIsInvalid() throws Exception {
        // Given
        AddOperationToRecordRequest request = new AddOperationToRecordRequest();

        // When & Then
        mockMvc.perform(post("/internal/v1/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists());
    }
}

