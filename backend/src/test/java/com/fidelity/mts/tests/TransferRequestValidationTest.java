package com.fidelity.mts.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import com.fidelity.mts.dto.TransferRequest;
 
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
 
class TransferRequestValidationTest {
 
    private Validator validator;
 
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
 
    private TransferRequest validRequest() {
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1);
        request.setToAccountId(2);
        request.setAmount(new BigDecimal("100.00"));
        request.setIdempotencyKey("idem-key-123");
        return request;
    }
 
    @Test
    void testValidRequest() {
        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(validRequest());
 
        assertTrue(violations.isEmpty());
    }
 
    @Test
    void testInvalidAmount() {
        TransferRequest request = validRequest();
        request.setAmount(new BigDecimal("0.00"));
 
        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(request);
 
        assertFalse(violations.isEmpty());
        assertTrue(
            violations.stream()
                .anyMatch(v -> v.getMessage().contains("amount must be at least 0.01"))
        );
    }
 
    @Test
    void testNullFields() {
        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(new TransferRequest());
 
        assertEquals(4, violations.size());
    }
}