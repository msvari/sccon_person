package com.poc.ex.service.impl;

import com.poc.ex.model.Person;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    private static final BigDecimal MIN_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal PERCENT_INCRISE_SALARY = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCRISE_SALARY = new BigDecimal("500.00");

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSavePerson() {
        Person person = new Person();
        personService.save(person);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void shouldFindPersonById() {
        Person person = new Person();
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(personRepository).findById(1L);
    }

    @Test
    void shouldFindAllPersonsOrderedByName() {
        List<Person> mockList = List.of(new Person(), new Person());
        when(personRepository.findAllByOrderByNameAsc()).thenReturn(mockList);

        List<Person> result = personService.findAllOrderByName();

        assertEquals(2, result.size());
        verify(personRepository).findAllByOrderByNameAsc();
    }

    @Test
    void shouldDeletePerson() {
        Person person = new Person();
        personService.delete(person);
        verify(personRepository).delete(person);
    }

    @Test
    void shouldCalculateAgeInYears() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        long age = personService.calculateAge(birthDate, AgeType.years);
        assertEquals(30, age);
    }

    @Test
    void shouldCalculateAgeInMonths() {
        LocalDate birthDate = LocalDate.now().minusMonths(18);
        long age = personService.calculateAge(birthDate, AgeType.months);
        assertEquals(18, age);
    }

    @Test
    void shouldCalculateAgeInDays() {
        LocalDate birthDate = LocalDate.now().minusDays(500);
        long age = personService.calculateAge(birthDate, AgeType.days);
        assertEquals(500, age);
    }

    @Test
    void shouldThrowExceptionForInvalidAgeType() {
        LocalDate birthDate = LocalDate.now().minusYears(10);
        assertThrows(IllegalArgumentException.class, () -> personService.calculateAge(birthDate, null));
    }

    @Test
    void shouldCalculateFullSalaryAfter3Years() {
        LocalDate hireDate = LocalDate.now().minusYears(3);

        BigDecimal salary = personService.calculateSalary(hireDate, SalaryType.full);

        BigDecimal expected = INITIAL_SALARY;
        for (int i = 0; i < 3; i++) {
            expected = expected.add(expected.multiply(PERCENT_INCRISE_SALARY).add(FIXED_INCRISE_SALARY));
        }

        assertEquals(0, expected.compareTo(salary));
    }

    @Test
    void shouldCalculateMinSalaryAfter2Years() {
        LocalDate hireDate = LocalDate.now().minusYears(2);

        BigDecimal fullSalary = personService.calculateSalary(hireDate, SalaryType.full);
        BigDecimal minSalary = personService.calculateSalary(hireDate, SalaryType.min);

        BigDecimal expected = fullSalary.divide(MIN_SALARY, 2, BigDecimal.ROUND_UP);
        assertEquals(0, expected.compareTo(minSalary));
    }

    @Test
    void shouldThrowExceptionForInvalidSalaryType() {
        LocalDate hireDate = LocalDate.now().minusYears(2);
        assertThrows(IllegalArgumentException.class, () -> personService.calculateSalary(hireDate, null));
    }

    @Test
    void shouldThrowExceptionForNullBirthDate() {
        assertThrows(IllegalArgumentException.class, () -> personService.calculateAge(null, AgeType.years));
    }

    @Test
    void shouldThrowExceptionForFutureBirthDate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> personService.calculateAge(futureDate, AgeType.years));
    }

    @Test
    void shouldThrowExceptionForNullHireDate() {
        assertThrows(IllegalArgumentException.class, () -> personService.calculateSalary(null, SalaryType.full));
    }

    @Test
    void shouldThrowExceptionForFutureHireDate() {
        LocalDate futureDate = LocalDate.now().plusMonths(1);
        assertThrows(IllegalArgumentException.class, () -> personService.calculateSalary(futureDate, SalaryType.full));
    }

}
