package com.example.restfulwebservice.user;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@RestController
public class UserController {

  private final UserDaoService userDaoService;

  @GetMapping(path = "/users")
  public List<User> retrieveAllUsers() {
    return userDaoService.findAll();
  }


  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable final Integer id) {
    User user = userDaoService.findOne(id);
    if (user == null) {
      throw new NotFoundUserException(String.format("ID[%s] Not Found", id));
    }
    //HATEOAS
    EntityModel<User> model = EntityModel.of(user);
    model.add(
        WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers())
            .withRel("all-users"));

    return model;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody final User user) {
    User savedUser = userDaoService.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();
    return ResponseEntity
        .created(location)
        .build();
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable int id) {
    User user = userDaoService.findOne(id);
    if (user == null) {
      throw new NotFoundUserException(String.format("ID[%s] Not Found", id));
    }
    userDaoService.deleteById(id);
  }

  @PutMapping("/users")
  public ResponseEntity<User> updateUser(@RequestBody final User user) {
    if (user.getId() == null) {
      throw new IllegalArgumentException(String.format("User.id is null"));
    }
    if (userDaoService.findOne(user.getId()) == null) {
      throw new NotFoundUserException(String.format("ID[%s] Not Found", user.getId()));
    }
    User savedUser = userDaoService.save(user);
    return ResponseEntity
        .ok()
        .body(savedUser);
  }
}
