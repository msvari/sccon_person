package com.poc.ex.validation.exception;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException() {
        super("Person already exists.");
    }
}
