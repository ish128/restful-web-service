package com.example.restfulwebservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Hello world controller.
 */
@RestController
public class HelloWorldController {


  /**
   * Hello world string.
   *
   * @return the string
   */
  @GetMapping(path = "/hello-world")
  public String helloWorld() {
    return "hello World!";
  }

  /**
   * Hello world bean hello world bean.
   *
   * @return the hello world bean
   */
  @GetMapping(path = "/hello-world-bean")
  public HelloWorldBean helloWorldBean() {
    return new HelloWorldBean("hello World!");
  }

  /**
   * Hello world bean hello world bean.
   *
   * @param name the name
   * @return the hello world bean
   */
  @GetMapping(path = "/hello-world-bean/{name}")
  public HelloWorldBean helloWorldBean(@PathVariable final String name) {
    return new HelloWorldBean(String.format("hello World, %s!", name));
  }
}
