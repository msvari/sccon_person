package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class DatabaseUpdateAllException extends RuntimeException {
    public DatabaseUpdateAllException() { super(ErrorValidation.INVALID_DATABASE_UPDATE_ALL_FIELDS_ERROR.getDescription()); }
}
