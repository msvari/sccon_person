package com.poc.ex.service.impl;

import com.poc.ex.mapper.PersonMapperService;
import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.repository.PersonRepository;
import com.poc.ex.service.PersonService;
import com.poc.ex.validation.exception.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private static final BigDecimal MIN_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal PERCENT_INCRISE_SALARY = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCRISE_SALARY = new BigDecimal("500.00");

    private final PersonRepository personRepository;
    private final PersonMapperService personMapperService;

    @Override
    public Optional<PersonDTO> findOnePerson(Long id) {
        log.info("state=init-find-person, id={}", id);
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        log.info("state=end-success-find-person-by-id , id={} ", id);
        return Optional.ofNullable(personMapperService.toPersonDTO(person));
    }

    @Override
    public List<PersonDTO> findAllPersonOrderByName() {
        log.info("state=init-find-all-persons");
        List<Person> persons = personRepository.findAllByOrderByNameAsc();
        log.info("state=end-success-find-all-persons");
        if(persons.isEmpty()) throw new PersonNotFoundException();
        return persons.stream().map(personMapperService::toPersonDTO).toList();
    }

    @Override
    public void savePerson(PersonDTO personDTO) {
        log.info("state=init-save-person, person={}", personDTO);
        Person person = personMapperService.toPerson(personDTO);
        personRepository.save(person);
        log.info("state=end-success-save-person, person={}", personDTO);
    }

    @Override
    public void updateAllFieldsPerson(Long id, PersonDTO personDTO) {
        log.info("state=init-update-person, person={}", personDTO);
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        var updatedPerson = personMapperService.toExistsPerson(person, personDTO, true);
        personRepository.save(updatedPerson);
        log.info("state=end-success-update-person, person={}", personDTO);
    }

    @Override
    public void updateSomeFieldsPerson(Long id, PersonDTO personDTO) {
        log.info("state=init-partial-update-person, person={}", personDTO);
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        var updatedPerson = personMapperService.toExistsPerson(person, personDTO, false);
        personRepository.save(updatedPerson);
        log.info("state=end-success-partial-update-person, person={}", personDTO);
    }

    @Override
    public void deletePerson(Long id) {
        log.info("state=init-delete-person, id={}", id);
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        log.info("state=end-success-delete-person, id={}", id);
    }

    @Override
    public long findPersonAge(Long id, AgeType ageType) {
        log.info("state=init-find-person-age , id={}", id);
        Optional<PersonDTO> personDTO = this.findOnePerson(id);
        long age = this.calculatePersonAge(personDTO.map(PersonDTO::birthDate).orElseThrow(IllegalArgumentException::new), ageType);
        log.info("state=end-success-find-person-age , id={} ", id);
        return age;
    }

    @Override
    public BigDecimal findPersonSalary(Long id, SalaryType salaryType) {
        log.info("state=init-find-person-salary , id={}", id);
        Optional<PersonDTO> personDTO = this.findOnePerson(id);
        BigDecimal salary = this.calculatePersonSalary(personDTO.map(PersonDTO::hireDate).orElseThrow(IllegalArgumentException::new), salaryType);
        log.info("state=end-success-find-person-salary , id={} ", id);
        return salary;
    }

    long calculatePersonAge(LocalDate birthDate, AgeType ageType) {
        this.validateDate(birthDate);
        if(ageType == null ) { throw new IllegalArgumentException("Invalid calculate age type"); }
        LocalDate today = LocalDate.now();
        return switch(ageType){
            case AgeType.days -> ChronoUnit.DAYS.between(birthDate, today);
            case AgeType.months -> ChronoUnit.MONTHS.between(birthDate, today);
            case AgeType.years -> ChronoUnit.YEARS.between(birthDate, today);
        };
    }

    BigDecimal calculatePersonSalary(LocalDate hireDate, SalaryType salaryType) {
        this.validateDate(hireDate);
        if(salaryType == null ) { throw new IllegalArgumentException("Invalid calculate salary type"); }
        return switch(salaryType){
            case SalaryType.full -> this.calculateFullSalary(hireDate);
            case SalaryType.min -> this.calculateMinSalary(hireDate);
        };
    }

    BigDecimal calculateFullSalary(LocalDate hireDate) {
        this.validateDate(hireDate);
        long hireYears = this.calculateHireYears(hireDate);
        BigDecimal salary = INITIAL_SALARY;
        while(hireYears > 0){
            salary = salary.add(salary.multiply(PERCENT_INCRISE_SALARY).add(FIXED_INCRISE_SALARY));
            hireYears--;
        }
        return salary;
    }

    BigDecimal calculateMinSalary(LocalDate hireDate) {
        this.validateDate(hireDate);
        BigDecimal fullSalary = this.calculateFullSalary(hireDate);
        return fullSalary.divide(MIN_SALARY, 2 , RoundingMode.UP);
    }

    void validateDate(LocalDate date) {
        if(Optional.ofNullable(date).isEmpty()){ throw new IllegalArgumentException("Invalid date"); }
        LocalDate today = LocalDate.now();
        if(date.isAfter(today)) { throw new IllegalArgumentException("Invalid future date"); }
    }

    Long calculateHireYears(LocalDate hireDate) {
        this.validateDate(hireDate);
        LocalDate today = LocalDate.now();
        return ChronoUnit.YEARS.between(hireDate, today);
    }

}
