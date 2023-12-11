package com.result;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void successWithData() {
        // Given
        String testData = "Test Data";

        // When
        Result<String> result = Result.success(testData);

        // Then
        assertTrue(result.isSuccess);
        assertEquals(testData, result.getData());
        assertTrue(result.getErrors().isEmpty());  // Check that error list is empty, not null
    }

    @Test
    void successWithDataAndMessage() {
        // Given
        String testData = "Test Data";
        String successMessage = "Operation successful";

        // When
        Result<String> result = Result.success(testData, successMessage);

        // Then
        assertTrue(result.isSuccess);
        assertEquals(testData, result.getData());
        assertEquals(successMessage, result.getSuccessMessage());
        assertTrue(result.getErrors().isEmpty());  // Check that error list is empty, not null
    }

    @Test
    void errorWithMessages() {
        // Given
        List<String> errorMessages = Arrays.asList("Error 1", "Error 2");

        // When
        Result<String> result = Result.error(errorMessages);

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.ERROR, result.getStatus());
        assertEquals(errorMessages, result.getErrors());
        assertNull(result.getData());
    }

    @Test
    void invalidWithValidationErrors() {
        // Given
        ValidationError validationError = new ValidationError("fieldName", "Invalid value");
        List<ValidationError> validationErrors = Arrays.asList(validationError);

        // When
        Result<String> result = Result.invalid(validationErrors);

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.INVALID, result.getStatus());
        assertEquals(validationErrors, result.getValidationErrors());
        assertNull(result.getData());
    }

    @Test
    void notFound() {
        // When
        Result<String> result = Result.notFound();

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.NOT_FOUND, result.getStatus());
        assertTrue(result.getErrors().isEmpty());
        assertNull(result.getData());
    }

    @Test
    void notFoundWithMessages() {
        // Given
        List<String> errorMessages = Arrays.asList("Resource not found");

        // When
        Result<String> result = Result.notFound(errorMessages);

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.NOT_FOUND, result.getStatus());
        assertEquals(errorMessages, result.getErrors());
        assertNull(result.getData());
    }

    @Test
    void forbidden() {
        // When
        Result<String> result = Result.forbidden();

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.FORBIDDEN, result.getStatus());
        assertTrue(result.getErrors().isEmpty());
        assertNull(result.getData());
    }

    @Test
    void forbiddenWithMessage() {
        // Given
        String errorMessage = "Access forbidden";

        // When
        Result<String> result = Result.forbidden(errorMessage);

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.FORBIDDEN, result.getStatus());
        assertEquals(Collections.singletonList(errorMessage), result.getErrors());
        assertNull(result.getData());
    }

    @Test
    void unauthorized() {
        // When
        Result<String> result = Result.unauthorized();

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.UNAUTHORIZED, result.getStatus());
        assertTrue(result.getErrors().isEmpty());
        assertNull(result.getData());
    }

    @Test
    void badRequest() {
        // When
        Result<String> result = Result.badRequest();

        // Then
        assertFalse(result.isSuccess);
        assertEquals(ResultStatus.BAD_REQUEST, result.getStatus());
        assertTrue(result.getErrors().isEmpty());
        assertNull(result.getData());
    }
}
