package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class PersonCalculateSalaryException extends RuntimeException {

    public PersonCalculateSalaryException() {
        super(ErrorValidation.INVALID_CALCULATE_SALARY_ERROR.getDescription());
    }

}
