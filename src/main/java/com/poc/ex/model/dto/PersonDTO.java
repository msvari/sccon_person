package com.poc.ex.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poc.ex.validation.annotation.BirthDateBeforeHireDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import java.time.LocalDate;

@Builder
@BirthDateBeforeHireDate
public record PersonDTO(@NotBlank String name,
                        @Past(message = "Past birth date required") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birthDate,
                        @Past(message = "Past hire date required") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate hireDate) {

}
