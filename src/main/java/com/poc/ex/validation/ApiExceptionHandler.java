package com.poc.ex.validation;

import com.poc.ex.model.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;

public interface ApiExceptionHandler {
    ResponseEntity<ErrorDTO> genericException(Exception ex);
    ResponseEntity<ErrorDTO> databaseSaveException(Exception ex);
    ResponseEntity<ErrorDTO> databaseUpdateAllException(Exception ex);
    ResponseEntity<ErrorDTO> databaseUpdateSomeException(Exception ex);
    ResponseEntity<ErrorDTO> databaseFindException(Exception ex);
    ResponseEntity<ErrorDTO> databaseDeleteException(Exception ex);
    ResponseEntity<ErrorDTO> personIllegalArgumentException(RuntimeException ex);
    ResponseEntity<ErrorDTO> personCalculateAgeException(RuntimeException ex);
    ResponseEntity<ErrorDTO> personCalculateSalaryException(RuntimeException ex);
    ResponseEntity<ErrorDTO> personNotFoundException(RuntimeException ex);
    ResponseEntity<ErrorDTO> personAlreadyExistsException(RuntimeException ex);
}
