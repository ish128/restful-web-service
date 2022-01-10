package com.example.restfulwebservice.helloworld;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Hello world controller.
 */
@RestController
public class HelloWorldController {

  /**
   * The Message source.
   */
  @Autowired
  private MessageSource messageSource;

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


  /**
   * Hello world internationalized hello world bean.
   *
   * @param locale the locale
   * @return the hello world bean
   */
  @GetMapping(path = "/hello-world-internationalized")
  public HelloWorldBean helloWorldInternationalized(
      @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    return new HelloWorldBean(messageSource.getMessage("greeting.message", null, locale));
  }

}
