package org.example.urlshortener.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLongUrl() {
        UrlRequestDTO dto = new UrlRequestDTO();
        dto.setLongUrl("http://testtest123.com");

        Set<ConstraintViolation<UrlRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Validation should not fail for a valid long URL.");
    }

    @Test
    void testInvalidLongUrlBlank() {
        UrlRequestDTO dto = new UrlRequestDTO();
        dto.setLongUrl(" ");

        Set<ConstraintViolation<UrlRequestDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Validation should fail for a blank long URL.");
        assertEquals("Long URL is required and cannot be blank.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidLongUrlNull() {
        UrlRequestDTO dto = new UrlRequestDTO();
        dto.setLongUrl(null);

        Set<ConstraintViolation<UrlRequestDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Validation should fail for a null long URL.");
        assertEquals("Long URL is required and cannot be blank.", violations.iterator().next().getMessage());
    }
}
