package com.poc.ex.validation.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() { super("Person not found."); }
}
