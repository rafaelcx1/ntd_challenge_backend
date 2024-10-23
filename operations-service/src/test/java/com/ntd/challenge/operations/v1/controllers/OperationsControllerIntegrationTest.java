package com.ntd.challenge.operations.v1.controllers;

import com.ntd.challenge.operations.v1.integrations.random_org.RandomOrgIntegration;
import com.ntd.challenge.operations.v1.integrations.record_service.RecordServiceIntegration;
import com.ntd.challenge.operations.v1.integrations.record_service.exceptions.RecordServiceForbiddenException;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.Record4XXResponse;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.enums.BadRequestErrorTypeEnum;
import com.ntd.challenge.operations.v1.repositories.OperationsRepository;
import com.ntd.challenge.operations.v1.security.utils.AuthContextUtils;
import com.ntd.challenge.operations.v1.utils.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails
public class OperationsControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private OperationsRepository operationsRepository;

    @MockBean
    private RecordServiceIntegration recordServiceIntegration;

    @MockBean
    private RandomOrgIntegration randomOrgIntegration;

    private static MockedStatic<AuthContextUtils> authContextMock;

    @BeforeAll
    public static void setUp() {
        authContextMock = mockStatic(AuthContextUtils.class);
        authContextMock.when(AuthContextUtils::getLoggedUserId).thenReturn(1);
    }

    @AfterAll
    public static void destroy() {
        authContextMock.clearInvocations();
    }

    @Test
    void addOperation_ShouldReturnAdditionResult() throws Exception {
        // When & Then
        mockMvc.perform(get("/v1/operations/add")
                        .param("value1", "10.5")
                        .param("value2", "5.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(16.0));
    }

    @Test
    void addOperation_ShouldReturnNotEnoughBalance() throws Exception {
        Record4XXResponse notEnoughBalanceResponse = new Record4XXResponse();
        notEnoughBalanceResponse.setTitle(BadRequestErrorTypeEnum.NOT_ENOUGH_BALANCE.name());

        doThrow(new RecordServiceForbiddenException(notEnoughBalanceResponse)).when(recordServiceIntegration).addOperationToRecord(any());

        // When & Then
        mockMvc.perform(get("/v1/operations/add")
                        .param("value1", "10.5")
                        .param("value2", "5.5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void subtractOperation_ShouldReturnSubtractionResult() throws Exception {
        // When & Then
        mockMvc.perform(get("/v1/operations/subtract")
                        .param("value1", "10.5")
                        .param("value2", "5.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void multiplyOperation_ShouldReturnMultiplicationResult() throws Exception {
        // When & Then
        mockMvc.perform(get("/v1/operations/multiply")
                        .param("value1", "10.5")
                        .param("value2", "5.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(57.75));
    }

    @Test
    void divisionOperation_ShouldReturnDivisionResult() throws Exception {
        // When & Then
        mockMvc.perform(get("/v1/operations/division")
                        .param("value1", "10.5")
                        .param("value2", "2.0")
                        .param("scale", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.25));
    }

    @Test
    void squareRootOperation_ShouldReturnSquareRootResult() throws Exception {
        // When & Then
        mockMvc.perform(get("/v1/operations/square-root")
                        .param("value", "16")
                        .param("precision", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(4.0));
    }

    @Test
    void randomStringOperation_ShouldReturnRandomString() throws Exception {
        when(randomOrgIntegration.getRandomString(anyInt())).thenReturn("RANDOMSTRING");

        // When & Then
        mockMvc.perform(get("/v1/operations/random-string")
                        .param("size", "12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("RANDOMSTRING"));
    }
}

