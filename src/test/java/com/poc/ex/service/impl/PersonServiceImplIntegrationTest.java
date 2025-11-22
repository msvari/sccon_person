package com.poc.ex.service.impl;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.repository.PersonRepository;
import com.poc.ex.service.PersonService;
import com.poc.ex.validation.exception.PersonIllegalArgumentException;
import com.poc.ex.validation.exception.PersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PersonServiceImplIntegrationTest {

    private static final BigDecimal MIN_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal PERCENT_INCRISE_SALARY = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCRISE_SALARY = new BigDecimal("500.00");

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    private Person persistPerson(String name, LocalDate birthDate, LocalDate hireDate) {
        Person p = Person.builder()
                .name(name)
                .birthDate(birthDate)
                .hireDate(hireDate)
                .updateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .createDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        return personRepository.save(p);
    }

    @Test
    @DisplayName("Should save and find one person")
    void shouldSaveAndFindPerson() {
        PersonDTO dto = PersonDTO.builder()
                .name("INTEGRA TEST")
                .birthDate(LocalDate.now().minusYears(25))
                .hireDate(LocalDate.now().minusYears(1))
                .build();

        Person saved = persistPerson(dto.name(), dto.birthDate(), dto.hireDate());

        Optional<PersonDTO> found = personService.findOnePerson(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("INTEGRA TEST", found.get().name());
    }

    @Test
    @DisplayName("Should save and find persons order by name")
    void shouldFindAllOrderedByName() {
        persistPerson("BRUNO", LocalDate.now().minusYears(20), LocalDate.now().minusYears(2));
        persistPerson("ANA", LocalDate.now().minusYears(22), LocalDate.now().minusYears(3));

        List<PersonDTO> list = personService.findAllPersonOrderByName();
        assertEquals(2, list.size());
        assertEquals("ANA", list.get(0).name());
        assertEquals("BRUNO", list.get(1).name());
    }

    @Test
    @DisplayName("Should persiste and delete one person")
    void shouldDeletePerson() {
        Person p = persistPerson("TO DELETE", LocalDate.now().minusYears(30), LocalDate.now().minusYears(5));
        personService.deletePerson(p.getId());
        assertFalse(personRepository.findById(p.getId()).isPresent());
    }

    @Test
    @DisplayName("Should throw a person not found exception")
    void shouldThrowWhenPersonNotFound() {
        assertThrows(PersonNotFoundException.class, () -> personService.findOnePerson(999999L));
    }

    @Test
    @DisplayName("Should calculate person year age")
    void shouldCalculateYearsAge() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        LocalDate hireDate = LocalDate.now().minusYears(3);

        Person p = persistPerson("CALC TEST", birthDate, hireDate);

        long age = personService.findPersonAge(p.getId(), AgeType.years);
        assertEquals(30, age);
    }

    @Test
    @DisplayName("Should calculate person full salary")
    void shouldCalculateFullSalary() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        LocalDate hireDate = LocalDate.now().minusYears(3);

        Person p = persistPerson("CALC TEST", birthDate, hireDate);

        BigDecimal salary = personService.findPersonSalary(p.getId(), SalaryType.full);

        BigDecimal expected = INITIAL_SALARY;
        BigDecimal percent = PERCENT_INCRISE_SALARY;
        BigDecimal fixed = FIXED_INCRISE_SALARY;
        for (int i = 0; i < 3; i++) {
            expected = expected.add(expected.multiply(percent).add(fixed));
        }
        assertEquals(0, expected.compareTo(salary));
    }

    @Test
    @DisplayName("Should calculate person min salary")
    void shouldCalculateMinSalary() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        LocalDate hireDate = LocalDate.now().minusYears(3);

        Person p = persistPerson("CALC TEST", birthDate, hireDate);

        BigDecimal expected = INITIAL_SALARY;
        BigDecimal percent = PERCENT_INCRISE_SALARY;
        BigDecimal fixed = FIXED_INCRISE_SALARY;
        for (int i = 0; i < 3; i++) {
            expected = expected.add(expected.multiply(percent).add(fixed));
        }

        BigDecimal minSalary = personService.findPersonSalary(p.getId(), SalaryType.min);
        BigDecimal expectedMin = expected.divide(MIN_SALARY, 2, BigDecimal.ROUND_UP);
        assertEquals(0, expectedMin.compareTo(minSalary));
    }

    @Test
    void shouldThrowWhenInvalidInputsForCalculations() {
        Person p = persistPerson("INVALIDS", LocalDate.now().minusYears(20), LocalDate.now().minusYears(1));
        assertThrows(PersonIllegalArgumentException.class, () -> personService.findPersonAge(p.getId(), null));
        assertThrows(PersonIllegalArgumentException.class, () -> personService.findPersonSalary(p.getId(), null));
    }

}
