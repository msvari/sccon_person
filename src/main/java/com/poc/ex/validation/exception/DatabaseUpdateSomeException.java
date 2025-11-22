package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class DatabaseUpdateSomeException extends RuntimeException {
    public DatabaseUpdateSomeException() { super(ErrorValidation.INVALID_DATABASE_UPDATE_SOME_FIELDS_ERROR.getDescription()); }
}
