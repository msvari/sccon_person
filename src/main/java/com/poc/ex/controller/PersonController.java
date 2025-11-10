package com.poc.ex.controller;

import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/person/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(personService.findOnePerson(id), HttpStatus.OK);
    }

    @GetMapping("/person/{id}/age")
    public ResponseEntity<Object> getAgePerson(@PathVariable(value = "id") Long id,
                                               @RequestParam AgeType ageType) {
        return new ResponseEntity<>(personService.findPersonAge(id, ageType), HttpStatus.OK);
    }

    @GetMapping("/person/{id}/salary")
    public ResponseEntity<Object> getSalaryPerson(@PathVariable(value = "id") Long id,
                                                  @RequestParam SalaryType salaryType) {
        return new ResponseEntity<>(personService.findPersonSalary(id, salaryType), HttpStatus.OK);
    }

    @GetMapping("/person")
    public ResponseEntity<Object> getPersons() {
        return new ResponseEntity<>(personService.findAllPersonOrderByName(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@Valid @RequestBody PersonDTO personDto) {
        personService.savePerson(personDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody PersonDTO personDto) {
        personService.updateAllFieldsPerson(id, personDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdatePerson(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody PersonDTO personDto) {
        personService.updateSomeFieldsPerson(id, personDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
