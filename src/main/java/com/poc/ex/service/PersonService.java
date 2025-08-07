package com.poc.ex.service;

import com.poc.ex.model.Person;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    void save(Person person);

    Optional<Person> findById(Long id);

    List<Person> findAllOrderByName();

    void delete(Person person);

    long calculateAge(LocalDate birthDate, AgeType ageType);

    BigDecimal calculateSalary(LocalDate hireDate, SalaryType salaryType);

}
