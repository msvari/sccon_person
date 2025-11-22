package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class DatabaseFindException extends RuntimeException {
    public DatabaseFindException() { super(ErrorValidation.INVALID_DATABASE_FIND_ERROR.getDescription()); }
}
