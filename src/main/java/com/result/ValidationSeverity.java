package com.result;

public enum ValidationSeverity {
    Error("Error"),
    Warning("Warning"),
    Info("Info");

    String value;

    ValidationSeverity(String value) {
        this.value = value;
    }
}
