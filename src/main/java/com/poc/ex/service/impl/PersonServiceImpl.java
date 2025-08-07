package com.poc.ex.service.impl;

import com.poc.ex.model.Person;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.repository.PersonRepository;
import com.poc.ex.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private static final BigDecimal MIN_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal PERCENT_INCRISE_SALARY = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCRISE_SALARY = new BigDecimal("500.00");

    private final PersonRepository personRepository;

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findById(Long id) { return personRepository.findById(id); }

    @Override
    public List<Person> findAllOrderByName() {
        return personRepository.findAllByOrderByNameAsc();
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public long calculateAge(LocalDate birthDate, AgeType ageType) {
        this.validateDate(birthDate);
        if(ageType == null ) { throw new IllegalArgumentException("Invalid calculate age type"); }
        LocalDate today = LocalDate.now();
        return switch(ageType){
            case AgeType.days -> ChronoUnit.DAYS.between(birthDate, today);
            case AgeType.months -> ChronoUnit.MONTHS.between(birthDate, today);
            case AgeType.years -> ChronoUnit.YEARS.between(birthDate, today);
            default -> throw new IllegalArgumentException("Invalid calculate age type");
        };
    }

    @Override
    public BigDecimal calculateSalary(LocalDate hireDate, SalaryType salaryType) {
        this.validateDate(hireDate);
        if(salaryType == null ) { throw new IllegalArgumentException("Invalid calculate salary type"); }
        LocalDate today = LocalDate.now();
        return switch(salaryType){
            case SalaryType.full -> this.calculateFullSalary(hireDate);
            case SalaryType.min -> this.calculateMinSalary(hireDate);
            default -> throw new IllegalArgumentException("Invalid calculate salary type");
        };
    }

    private BigDecimal calculateFullSalary(LocalDate hireDate) {
        this.validateDate(hireDate);
        long hireYears = this.calculateHireYears(hireDate);
        BigDecimal salary = INITIAL_SALARY;
        while(hireYears > 0){
            salary = salary.add(salary.multiply(PERCENT_INCRISE_SALARY).add(FIXED_INCRISE_SALARY));
            hireYears--;
        }
        return salary;
    }

    private BigDecimal calculateMinSalary(LocalDate hireDate) {
        this.validateDate(hireDate);
        BigDecimal fullSalary = this.calculateFullSalary(hireDate);
        return fullSalary.divide(MIN_SALARY, 2 , RoundingMode.UP);
    }

    private void validateDate(LocalDate date) {
        if(Optional.ofNullable(date).isEmpty()){ throw new IllegalArgumentException("Invalid date"); }
        LocalDate today = LocalDate.now();
        if(date.isAfter(today)) { throw new IllegalArgumentException("Invalid future date"); }
    }

    private Long calculateHireYears(LocalDate hireDate) {
        this.validateDate(hireDate);
        LocalDate today = LocalDate.now();
        return ChronoUnit.YEARS.between(hireDate, today);
    }

}
