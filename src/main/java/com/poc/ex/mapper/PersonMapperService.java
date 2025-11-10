package com.poc.ex.mapper;

import com.poc.ex.model.Person;
import com.poc.ex.model.dto.PersonDTO;

public interface PersonMapperService {
    PersonDTO toPersonDTO(Person person);
    Person toPerson(PersonDTO personDTO);
    Person toExistsPerson(Person person, PersonDTO personDTO, boolean isUpdateAll);
}
