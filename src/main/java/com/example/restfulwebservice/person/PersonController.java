package com.example.restfulwebservice.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * for spring boot hateoas test
 */
@RestController
public class PersonController {

  /**
   * 1. RepresentationModel 을 상속하여, hateoas 적용된 응답 리턴
   *
   * @return the person model
   */
  @GetMapping("/persons/1")
  public PersonModel person1() {
    PersonModel model = new PersonModel();
    model.setFirstname("Dave");
    model.setLastname("Matthews");
    model.add(Link.of("https://myhost/persons/1"));
    return model;
  }

  /**
   * 2. Item resource representation model
   *
   * @return the entity model
   */
  @GetMapping("/persons/2")
  public EntityModel<Person> person2() {
    Person person = new Person("Dave2", "Matthews2");
    EntityModel<Person> model = EntityModel.of(person);
    model.add(Link.of("https://myhost/persons/2"));
    return model;
  }

  /**
   * 3. Collection resource representation model
   *
   * @return the collection model
   */
  @GetMapping("/persons")
  public CollectionModel<Person> persons() {
    List<Person> list = new ArrayList<>();
    Collections.addAll(list,
        new Person("Dave", "Matthews"),
        new Person("Dave1", "Matthews1"));
    CollectionModel<Person> model = CollectionModel.of(list);
    model.add(Link.of("https://myhost/persons"));
    model.add(Link.of("https://myhost/persons", LinkRelation.of("create")));
    return model;
  }
}
