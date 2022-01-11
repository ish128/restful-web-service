package com.example.restfulwebservice.person;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class PersonModel extends RepresentationModel<PersonModel> {

  private String firstname, lastname;
}
