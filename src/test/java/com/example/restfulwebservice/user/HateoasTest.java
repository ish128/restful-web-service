package com.example.restfulwebservice.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;

/**
 * The type Hateoas test.
 */
class HateoasTest {


  @Test
  public void test() {
    Link link = linkTo(UserController.class).withRel("people");

    assertThat(link.getRel()).isEqualTo(LinkRelation.of("people"));
    assertThat(link.getHref()).endsWith("/people");
  }


}