package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

  private final UserDaoService userDaoService;

  @GetMapping(path = "/users")
  public MappingJacksonValue retrieveAllUsers() {
    List<User> users = userDaoService.findAll();

    SimpleBeanPropertyFilter filter =
        SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "address");
    FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }

  @GetMapping("/users/{id}")
  public MappingJacksonValue retrieveUser(@PathVariable final Integer id) {
    User user = userDaoService.findOne(id);
    if (user == null) {
      throw new NotFoundUserException(String.format("ID[%s] Not Found", id));
    }
    SimpleBeanPropertyFilter filter =
        SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "address");
    FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }

}
