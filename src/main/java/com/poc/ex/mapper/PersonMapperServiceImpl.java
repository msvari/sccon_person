package com.poc.ex.mapper;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.validation.exception.PersonNotFoundException;
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
    public Person toExistsPerson(Person person, PersonDTO personDTO, boolean isUpdateAll) {
        if(Optional.ofNullable(person).isEmpty()) throw  new PersonNotFoundException();
        return isUpdateAll ? this.toAllFieldsExistsPerson(person, personDTO) : this.toSomeFieldsExistsPerson(person, personDTO);
    }

    private Person toAllFieldsExistsPerson(Person person, PersonDTO personDTO) {
        person.setName(personDTO.name());
        person.setBirthDate(personDTO.birthDate());
        person.setHireDate(personDTO.hireDate());
        person.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return person;
    }

    private Person toSomeFieldsExistsPerson(Person person, PersonDTO personDTO) {
        person.setName(!StringUtils.isBlank(personDTO.name()) ? personDTO.name() : person.getName());
        person.setBirthDate(Optional.ofNullable(personDTO.birthDate()).isPresent() ? personDTO.birthDate() : person.getBirthDate());
        person.setHireDate(Optional.ofNullable(personDTO.hireDate()).isPresent() ? personDTO.hireDate() : person.getHireDate());
        person.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return person;
    }

}
