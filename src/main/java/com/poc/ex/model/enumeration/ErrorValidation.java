package com.poc.ex.model.enumeration;

import lombok.Getter;

@Getter
public enum ErrorValidation {

    INVALID_PERSON_ILLEGAL_ARGUMENT_ERROR("ERR000", "Invalid person data."),
    INVALID_PERSON_ILLEGAL_ARGUMENT_EMPTY_DATE("ERR001", "Invalid empty date."),
    INVALID_PERSON_ILLEGAL_ARGUMENT_FUTURE_DATE("ERR002", "Invalid future date."),
    INVALID_PERSON_ILLEGAL_ARGUMENT_AGE_TYPE("ERR003", "Invalid age type."),
    INVALID_PERSON_ILLEGAL_ARGUMENT_SALARY_TYPE("ERR004", "Invalid salary type."),
    INVALID_CALCULATE_SALARY_ERROR("ERR005", "Calculate salary error."),
    INVALID_CALCULATE_AGE_ERROR("ERR006", "Calculate age error."),
    INVALID_PERSON_ALREADY_EXISTS_ERROR("ERR0409", "Person already exists."),
    INVALID_PERSON_NOT_FOUND_ERROR("ERR0404", "Person not found."),
    INVALID_INTERNAL_SERVER_ERROR("ERR0500", "Person not found."),
    INVALID_DATABASE_SAVE_ERROR("ERR007", "Database save error."),
    INVALID_DATABASE_UPDATE_ALL_FIELDS_ERROR("ERR008", "Database update all fiels error."),
    INVALID_DATABASE_UPDATE_SOME_FIELDS_ERROR("ERR009", "Database update some fiels error."),
    INVALID_DATABASE_DELETE_ERROR("ERR010", "Database delete error."),
    INVALID_DATABASE_FIND_ERROR("ERR011", "Database search error.");

    private final String code;
    private final String description;

    ErrorValidation(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
