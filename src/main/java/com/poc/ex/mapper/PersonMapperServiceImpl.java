package com.poc.ex.mapper;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class PersonMapperServiceImpl implements PersonMapperService {

    @Override
    public PersonDTO toPersonDTO(Person person) {
        return new PersonDTO(person.getName(), person.getBirthDate(), person.getHireDate());
    }

    @Override
    public Person toPerson(PersonDTO personDTO) {
        return Person.builder()
                .name(personDTO.name())
                .birthDate(personDTO.birthDate())
                .hireDate(personDTO.hireDate())
                .updateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .createDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    @Override
    public Person toExistsPerson(Optional<Person> person, PersonDTO personDTO, boolean isUpdateAll) {
        return isUpdateAll ? this.toAllFieldsExistsPerson(person, personDTO) : this.toSomeFieldsExistsPerson(person, personDTO);
    }

    private Person toAllFieldsExistsPerson(Optional<Person> person, PersonDTO personDTO) {
        var updatedPerson = person.orElseThrow(() -> new EntityNotFoundException("Person not found"));
        updatedPerson.setName(personDTO.name());
        updatedPerson.setBirthDate(personDTO.birthDate());
        updatedPerson.setHireDate(personDTO.hireDate());
        updatedPerson.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return updatedPerson;
    }

    private Person toSomeFieldsExistsPerson(Optional<Person> person, PersonDTO personDTO) {
        var updatedPerson = person.orElseThrow(() -> new EntityNotFoundException("Person not found"));
        updatedPerson.setName(!StringUtils.isBlank(personDTO.name()) ? personDTO.name() : person.map(Person::getName).orElseThrow(IllegalArgumentException::new));
        updatedPerson.setBirthDate(Optional.ofNullable(personDTO.birthDate()).isPresent() ? personDTO.birthDate() : person.map(Person::getBirthDate).orElseThrow(IllegalArgumentException::new));
        updatedPerson.setHireDate(Optional.ofNullable(personDTO.hireDate()).isPresent() ? personDTO.hireDate() : person.map(Person::getHireDate).orElseThrow(IllegalArgumentException::new));
        updatedPerson.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return updatedPerson;
    }

}
