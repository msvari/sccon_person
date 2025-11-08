package com.poc.ex.mapper;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;

import java.util.Optional;

public interface PersonMapperService {
    PersonDTO toPersonDTO(Person person);
    Person toPerson(PersonDTO personDTO);
    Person toExistsPerson(Optional<Person> person, PersonDTO personDTO, boolean isUpdateAll);
}
