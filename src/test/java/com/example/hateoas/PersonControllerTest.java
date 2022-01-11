package com.example.hateoas;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.example.restfulwebservice.person.PersonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.filter.CharacterEncodingFilter;


public class PersonControllerTest {

  private MockMvc mockMvc;

  @BeforeEach
  void initEach() {
    this.mockMvc = standaloneSetup(new PersonController()).addFilter(
            new CharacterEncodingFilter("UTF-8", true))
        .defaultRequest(get("/person/1")
            .accept("application/hal+json")
            .characterEncoding("UTF-8"))
        .alwaysExpect(status().isOk())
        .alwaysExpect(content().contentType("application/hal+json")).alwaysDo(print())
        .build();
  }

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(
            get("/person/1").header("Accept", "application/hal+json"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("_links").exists());
  }

}
