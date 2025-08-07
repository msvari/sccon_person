package com.poc.ex.repository;

import com.poc.ex.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person , Long> {

    List<Person> findAllByOrderByNameAsc();

}
