// java
package com.poc.ex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.ex.model.dto.PersonDTO;
import com.poc.ex.model.enumeration.AgeType;
import com.poc.ex.model.enumeration.SalaryType;
import com.poc.ex.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("Should return one person")
    void getOnePersonReturnsPersonDto() throws Exception {
        PersonDTO dto = PersonDTO.builder()
                .name("Teste")
                .birthDate(LocalDate.of(1990, 1, 1))
                .hireDate(LocalDate.of(2020, 1, 1))
                .build();

        given(personService.findOnePerson(1L)).willReturn(Optional.of(dto));

        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Teste"));
    }

    @Test
    @DisplayName("Should return person age years")
    void getAgePersonReturnsNumber() throws Exception {
        given(personService.findPersonAge(2L, AgeType.years)).willReturn(30L);

        mockMvc.perform(get("/person/2/age").param("ageType", "years"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("30")));
    }

    @Test
    @DisplayName("Should return person full salary")
    void getSalaryPersonReturnsBigDecimal() throws Exception {
        BigDecimal salary = new BigDecimal("1234.56");
        given(personService.findPersonSalary(3L, SalaryType.full)).willReturn(salary);

        mockMvc.perform(get("/person/3/salary").param("salaryType", "full"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1234.56")));
    }

    @Test
    @DisplayName("Should return all persons")
    void getPersonsReturnsList() throws Exception {
        PersonDTO a = PersonDTO.builder()
                .name("Ana")
                .birthDate(LocalDate.of(1995, 5, 5))
                .hireDate(LocalDate.of(2021, 1, 1))
                .build();
        PersonDTO b = PersonDTO.builder()
                .name("Bruno")
                .birthDate(LocalDate.of(1992, 2, 2))
                .hireDate(LocalDate.of(2019, 1, 1))
                .build();

        given(personService.findAllPersonOrderByName()).willReturn(List.of(a, b));

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ana"))
                .andExpect(jsonPath("$[1].name").value("Bruno"));
    }

    @Test
    @DisplayName("Should create one person")
    void postSavePersonCallsServiceAndReturnsCreated() throws Exception {
        PersonDTO dto = PersonDTO.builder()
                .name("NEW PERSON NAME")
                .birthDate(LocalDate.of(2000, 3, 3))
                .hireDate(LocalDate.of(2023, 1, 1))
                .build();

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        ArgumentCaptor<PersonDTO> captor = ArgumentCaptor.forClass(PersonDTO.class);
        verify(personService).savePerson(captor.capture());
    }

    @Test
    @DisplayName("Should update all person fields")
    void putUpdatePersonCallsServiceAndReturnsNoContent() throws Exception {
        PersonDTO dto = PersonDTO.builder()
                .name("UPDATE PERSON NAME")
                .birthDate(LocalDate.of(1991, 4, 4))
                .hireDate(LocalDate.of(2022, 1, 1))
                .build();

        mockMvc.perform(put("/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(personService).updateAllFieldsPerson(eq(5L), any(PersonDTO.class));
    }

    @Test
    @DisplayName("Should update some person fields")
    void patchPartialUpdatePersonCallsServiceAndReturnsNoContent() throws Exception {
        PersonDTO dto = PersonDTO.builder()
                .name("PARTIAL UPDATE PERSON NAME")
                .birthDate(LocalDate.of(1993, 6, 6))
                .hireDate(LocalDate.of(2020, 6, 1))
                .build();

        mockMvc.perform(patch("/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(personService).updateSomeFieldsPerson(eq(6L), any(PersonDTO.class));
    }

    @Test
    @DisplayName("Should delete one person")
    void deletePersonCallsServiceAndReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/person/9"))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson(9L);
    }

}