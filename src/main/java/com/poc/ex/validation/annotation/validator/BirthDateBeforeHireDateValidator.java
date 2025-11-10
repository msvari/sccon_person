package com.poc.ex.validation.annotation.validator;

import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.validation.annotation.BirthDateBeforeHireDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BirthDateBeforeHireDateValidator implements ConstraintValidator<BirthDateBeforeHireDate, PersonDTO> {

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext context) {
        if (personDTO.birthDate() == null || personDTO.hireDate() == null) {
            return true;
        }
        return personDTO.birthDate().isBefore(personDTO.hireDate());
    }

}
