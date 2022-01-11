package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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

  //@GetMapping("/v1/users/{id}")
  //@GetMapping(value = "/users/{id}", params = "version=1")
  //@GetMapping(value = "/users/{id}", headers = "x-api-version=1")
  @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
  public MappingJacksonValue retrieveUserV1(@PathVariable final Integer id) {
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

  //@GetMapping("/v2/users/{id}")
  //@GetMapping(value = "/users/{id}", params = "version=2")
  //@GetMapping(value = "/users/{id}", headers = "x-api-version=2")
  @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
  public MappingJacksonValue retrieveUserV2(@PathVariable final Integer id) {
    User user = userDaoService.findOne(id);
    if (user == null) {
      throw new NotFoundUserException(String.format("ID[%s] Not Found", id));
    }
    UserV2 userV2 = new UserV2();
    BeanUtils.copyProperties(user, userV2);
    userV2.setGrade("VIP");

    SimpleBeanPropertyFilter filter =
        SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "address", "grade");
    FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }

}
