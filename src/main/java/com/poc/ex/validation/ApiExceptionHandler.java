package com.poc.ex.validation;

import com.poc.ex.model.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;

public interface ApiExceptionHandler {
    ResponseEntity<ErrorDTO> genericException(Exception ex);
    ResponseEntity<ErrorDTO> notFoundException(RuntimeException ex);
    ResponseEntity<ErrorDTO> personAlreadyExistsException(RuntimeException ex);
}
