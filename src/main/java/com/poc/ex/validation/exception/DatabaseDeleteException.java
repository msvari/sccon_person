package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class DatabaseDeleteException extends RuntimeException {
    public DatabaseDeleteException() { super(ErrorValidation.INVALID_DATABASE_DELETE_ERROR.getDescription()); }
}
