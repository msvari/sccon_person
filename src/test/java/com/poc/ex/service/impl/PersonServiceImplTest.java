package com.poc.ex.service.impl;

import com.poc.ex.mapper.PersonMapperService;
import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PersonServiceImplTest {

    private static final BigDecimal MIN_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal PERCENT_INCRISE_SALARY = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCRISE_SALARY = new BigDecimal("500.00");

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapperService personMapperService;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSavePerson() {
        Person mockPerson = Person.builder().id(1L).name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();
        PersonDTO mockPersonDTO = PersonDTO.builder().name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();

        when(personMapperService.toPerson(mockPersonDTO)).thenReturn(mockPerson);

        personService.savePerson(mockPersonDTO);
        verify(personRepository, times(1)).save(mockPerson);
    }

    @Test
    void shouldFindPersonById() {
        Person mockPerson = Person.builder().name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();
        PersonDTO mockPersonDTO = PersonDTO.builder().name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();

        when(personMapperService.toPersonDTO(mockPerson)).thenReturn(mockPersonDTO);
        when(personRepository.findById(1L)).thenReturn(Optional.of(mockPerson));
        Optional<PersonDTO> result = personService.findOnePerson(1L);

        assertTrue(result.isPresent());
        verify(personRepository).findById(1L);
    }

    @Test
    void shouldFindAllPersonsOrderedByName() {
        Person mockPerson = Person.builder().name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();
        PersonDTO mockPersonDTO = PersonDTO.builder().name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();
        List<Person> mockPersonList = List.of(mockPerson);

        when(personMapperService.toPersonDTO(mockPerson)).thenReturn(mockPersonDTO);
        when(personRepository.findAllByOrderByNameAsc()).thenReturn(mockPersonList);
        List<PersonDTO> result = personService.findAllPersonOrderByName();

        assertEquals(1, result.size());
        verify(personRepository).findAllByOrderByNameAsc();
    }

    @Test
    void shouldDeletePerson() {
        Person mockPerson = Person.builder().id(1L).name("Person name").birthDate(LocalDate.now()).hireDate(LocalDate.now()).build();

        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(mockPerson));
        personService.deletePerson(1L);

        assertNotNull(mockPerson);
        verify(personRepository).delete(mockPerson);
    }

    @Test
    void shouldCalculateAgeInYears() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        long age = personService.calculatePersonAge(birthDate, AgeType.years);
        assertEquals(30, age);
    }

    @Test
    void shouldCalculateAgeInMonths() {
        LocalDate birthDate = LocalDate.now().minusMonths(18);
        long age = personService.calculatePersonAge(birthDate, AgeType.months);
        assertEquals(18, age);
    }

    @Test
    void shouldCalculateAgeInDays() {
        LocalDate birthDate = LocalDate.now().minusDays(500);
        long age = personService.calculatePersonAge(birthDate, AgeType.days);
        assertEquals(500, age);
    }

    @Test
    void shouldThrowExceptionForInvalidAgeType() {
        LocalDate birthDate = LocalDate.now().minusYears(10);
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonAge(birthDate, null));
    }

    @Test
    void shouldCalculateFullSalaryAfter3Years() {
        LocalDate hireDate = LocalDate.now().minusYears(3);

        BigDecimal salary = personService.calculatePersonSalary(hireDate, SalaryType.full);

        BigDecimal expected = INITIAL_SALARY;
        for (int i = 0; i < 3; i++) {
            expected = expected.add(expected.multiply(PERCENT_INCRISE_SALARY).add(FIXED_INCRISE_SALARY));
        }

        assertEquals(0, expected.compareTo(salary));
    }

    @Test
    void shouldCalculateMinSalaryAfter2Years() {
        LocalDate hireDate = LocalDate.now().minusYears(2);

        BigDecimal fullSalary = personService.calculatePersonSalary(hireDate, SalaryType.full);
        BigDecimal minSalary = personService.calculatePersonSalary(hireDate, SalaryType.min);

        BigDecimal expected = fullSalary.divide(MIN_SALARY, 2, BigDecimal.ROUND_UP);
        assertEquals(0, expected.compareTo(minSalary));
    }

    @Test
    void shouldThrowExceptionForInvalidSalaryType() {
        LocalDate hireDate = LocalDate.now().minusYears(2);
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonSalary(hireDate, null));
    }

    @Test
    void shouldThrowExceptionForNullBirthDate() {
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonAge(null, AgeType.years));
    }

    @Test
    void shouldThrowExceptionForFutureBirthDate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonAge(futureDate, AgeType.years));
    }

    @Test
    void shouldThrowExceptionForNullHireDate() {
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonSalary(null, SalaryType.full));
    }

    @Test
    void shouldThrowExceptionForFutureHireDate() {
        LocalDate futureDate = LocalDate.now().plusMonths(1);
        assertThrows(IllegalArgumentException.class, () -> personService.calculatePersonSalary(futureDate, SalaryType.full));
    }

}
