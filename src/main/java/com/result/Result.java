package com.result;

import br.com.fluentvalidator.context.Error;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Result<T> {

    T data;

    ResultStatus status = ResultStatus.OK;
    public boolean isSuccess = true;
  
    private String successMessage = "";
    private List<String> errors = new ArrayList<String>();
    private List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    public String getSuccessMessage() {
        return successMessage;
    }

    private void setIsSuccess() {
        isSuccess = status == ResultStatus.OK;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    protected Result() {
    }

    public Result(T value) {
        data = value;
    }

    protected Result(T value, String successMessage) {
        this(value);
        this.successMessage = successMessage;
        this.setIsSuccess();
    }

    protected Result(ResultStatus status) {
        this.status = status;
        this.setIsSuccess();
    }

    public static <T> T _v(Result<T> result) {
        return result.data;
    }

    public T getData() {
        return data;
    }

    public static <T> Result<T> success(T value) {
        return new Result<T>(value);
    }

    public static <T> Result<T> success(T value, String successMessage) {
        return new Result<T>(value, successMessage);
    }

    public static <T> Result<T> error(List<String> errorMessages) {
        Result<T> result = new Result<T>(ResultStatus.ERROR);
        result.errors = errorMessages;
        return result;
    }

    public static <T> Result<T> error(List<String> errorMessages, int resultStatusCode) {
        ResultStatus status = ResultStatus.valueOf(resultStatusCode);
        Result<T> result = new Result<T>(status);
        result.errors = errorMessages;
        return result;
    }

    public static <T> Result<T> error(String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        return error(errors);
    }

    // This method is used to integrate the fluent validation error collection
    public static <T> Result<T> invalid(Collection<Error> errors) {
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        for (Error error : errors) {
            ValidationError validationError = new ValidationError();
            validationError.setErrorCode(error.getCode());
            validationError.setErrorMessage(error.getMessage());
            validationError.setIdentifier(error.getField());
            validationErrors.add(validationError);
        }

        Result<T> result = new Result<T>(ResultStatus.INVALID);
        result.validationErrors = validationErrors;
        return result;
    }

    public static <T> Result<T> invalid(String errorString) {
        ResultStatus status = ResultStatus.BAD_REQUEST;
        Result<T> result = new Result<T>(status);
        List<String> errors = new ArrayList<>();
        errors.add(errorString);
        result.errors = errors;
        return result;
    }

    public static <T> Result<T> invalid(List<ValidationError> validationErrors) {
        Result<T> result = new Result<T>(ResultStatus.INVALID);
        result.validationErrors = validationErrors;
        return result;
    }

    public static <T> Result<T> notFound() {
        return new Result<T>(ResultStatus.NOT_FOUND);
    }

    public static <T> Result<T> notFound(String errorMessage) {
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        return notFound(errorMessages);
    }

    public static <T> Result<T> notFound(List<String> errorMessages) {
        Result<T> result = new Result<T>(ResultStatus.NOT_FOUND);
        result.errors = errorMessages;
        return result;
    }

    public static <T> Result<T> forbidden() {
        return new Result<T>(ResultStatus.FORBIDDEN);
    }

    public static <T> Result<T> forbidden(String errorMessage) {
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        Result<T> result = new Result<T>(ResultStatus.FORBIDDEN);
        result.errors = errorMessages;
        return result;
    }

    public static <T> Result<T> unauthorized() {
        return new Result<T>(ResultStatus.UNAUTHORIZED);
    }

    public static <T> Result<T> badRequest() {
        return new Result<T>(ResultStatus.BAD_REQUEST);
    }

}
