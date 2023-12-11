package com.result;

public class ValidationError {
    private String identifier;
    private String errorMessage;
    private String errorCode;
    private String severity;

    public ValidationError() {
        // Default constructor
    }

    public ValidationError(String identifier, String errorMessage) {
        this.identifier = identifier;
        this.errorMessage = errorMessage;
        this.severity = ValidationSeverity.Error.value;
    }

    public ValidationError(String identifier, String errorMessage, String errorCode, String severity) {
        this.identifier = identifier;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.severity = severity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
