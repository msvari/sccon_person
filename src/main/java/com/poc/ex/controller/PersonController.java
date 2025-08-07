package com.poc.ex.controller;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/person/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") Long id) {
        log.info("state=init-find-person , id={}", id);
        try {
            Optional<Person> person = personService.findById(id);
            log.info("state=end-success-find-person-by-id , id={} ", id);
            return person.<ResponseEntity<Object>>map(item -> ResponseEntity.status(HttpStatus.OK).body(item)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found."));
        } catch (Exception e) {
            log.error("state=error-find-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server erro");
        }
    }

    @GetMapping("/person/{id}/age")
    public ResponseEntity<Object> getAgePerson(@PathVariable(value = "id") Long id,
                                               @RequestParam AgeType output) {
        log.info("state=init-find-age-person , id={}", id);
        try {
            Optional<Person> person = personService.findById(id);

            log.info("state=end-success-find-age-person-by-id , id={} ", id);
            return person.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.")
                    : ResponseEntity.status(HttpStatus.OK).body(personService.calculateAge(person.map(Person::getBirthDate).orElseThrow(IllegalArgumentException::new), output));
        } catch (Exception e) {
            log.error("state=error-find-age-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server erro");
        }
    }

    @GetMapping("/person/{id}/salary")
    public ResponseEntity<Object> getSalaryPerson(@PathVariable(value = "id") Long id,
                                               @RequestParam SalaryType output) {
        log.info("state=init-find-salary-person , id={}", id);
        try {
            Optional<Person> person = personService.findById(id);
            log.info("state=end-success-find-salary-person-by-id , id={} ", id);
            return person.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.")
                    : ResponseEntity.status(HttpStatus.OK).body(personService.calculateSalary(person.map(Person::getHireDate).orElseThrow(IllegalArgumentException::new), output));
        } catch (Exception e) {
            log.error("state=error-find-salary-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server erro");
        }
    }

    @GetMapping("/person")
    public ResponseEntity<Object> getPersons() {
        log.info("state=init-find-all-person");
        try {
            List<Person> persons = personService.findAllOrderByName();
            log.info("state=end-success-find-all-person");

            return persons.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.")
                    : ResponseEntity.status(HttpStatus.OK).body(persons);
        } catch (Exception e) {
            log.error("state=error-find-all-person", e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestParam String name,
                                             @RequestParam LocalDate birthDate,
                                             @RequestParam LocalDate hireDate) {

        log.info("state=init-save-person , name={}, birth_date={}, hire_date={}", name, birthDate, hireDate);
        try {

            var person = Person.builder()
                    .name(name)
                    .birthDate(birthDate)
                    .hireDate(hireDate)
                    .updateDate(LocalDateTime.now(ZoneId.of("UTC")))
                    .createDate(LocalDateTime.now(ZoneId.of("UTC")))
                    .build();
            personService.save(person);
            log.info("state=end-success-save-person , name={}, birth_date={}, hire_date={}", name, birthDate, hireDate);

            return ResponseEntity.status(HttpStatus.CREATED).body(person);
        } catch (Exception e) {
            log.error("state=error-save-person, name={}, birth_date={}, hire_date={}", name, birthDate, hireDate, e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") Long id,
                                             @RequestParam String name,
                                             @RequestParam LocalDate birthDate,
                                             @RequestParam LocalDate hireDate) {

        log.info("state=init-update-person , id={}", id);
        try {
            Optional<Person> person = personService.findById(id);
            if(person.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
            }

            var updatedPerson = person.get();
            updatedPerson.setName(name);
            updatedPerson.setBirthDate(birthDate);
            updatedPerson.setHireDate(hireDate);
            updatedPerson.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            personService.save(updatedPerson);
            log.info("state=end-success-update-person , id={} ", id);

            return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
        } catch (Exception e) {
            log.error("state=error-update-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdatePerson(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody PersonDTO personDto) {

        log.info("state=init-partial-update-person , id={}", id);
        try {

            Optional<Person> person = personService.findById(id);
            if(person.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
            }

            var updatedPerson = person.get();
            updatedPerson.setName(!StringUtils.isBlank(personDto.name()) ? personDto.name() : person.map(Person::getName).orElseThrow(IllegalArgumentException::new));
            updatedPerson.setBirthDate(Optional.ofNullable(personDto.birthDate()).isPresent() ? personDto.birthDate() : person.map(Person::getBirthDate).orElseThrow(IllegalArgumentException::new));
            updatedPerson.setHireDate(Optional.ofNullable(personDto.hireDate()).isPresent() ? personDto.hireDate() : person.map(Person::getHireDate).orElseThrow(IllegalArgumentException::new));
            updatedPerson.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            personService.save(updatedPerson);
            log.info("state=end-success-partial-update-person , id={} ", id);

            return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
        } catch (Exception e) {
            log.error("state=error-partial-update-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") Long id) {

        log.info("state=init-delete-person , id={}", id);
        try {
            Optional<Person> person = personService.findById(id);
            if(person.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
            }

            personService.delete(person.get());
            log.info("state=end-success-delete-person , id={} ", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("state=error-delete-person, id={}", id , e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

}
