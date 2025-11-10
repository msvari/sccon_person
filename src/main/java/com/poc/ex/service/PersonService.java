package com.poc.ex.service;

import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<PersonDTO> findOnePerson(Long id);
    List<PersonDTO> findAllPersonOrderByName();
    void savePerson(PersonDTO personDTO);
    void updateAllFieldsPerson(Long id, PersonDTO personDTO);
    void updateSomeFieldsPerson(Long id, PersonDTO personDTO);
    void deletePerson(Long id);
    long findPersonAge(Long id, AgeType ageType);
    BigDecimal findPersonSalary(Long id, SalaryType salaryType);
}
