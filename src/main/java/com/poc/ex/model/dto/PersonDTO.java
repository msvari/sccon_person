package com.poc.ex.model.dto;

import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record PersonDTO(String name,
                        @Past(message = "Past birth date required") LocalDate birthDate,
                        @Past(message = "Past hire date required") LocalDate hireDate) {

}
