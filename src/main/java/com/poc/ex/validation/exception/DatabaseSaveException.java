package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class DatabaseSaveException extends RuntimeException {
    public DatabaseSaveException() { super(ErrorValidation.INVALID_DATABASE_SAVE_ERROR.getDescription()); }
}
