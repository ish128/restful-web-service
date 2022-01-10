package com.example.restfulwebservice.test;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@Slf4j
@AllArgsConstructor
@RestController
public class MessageTestController {

  private final MessageSource messageSource;

  @GetMapping("/messages")
  public Set<String> test() {
    String success = messageSource.getMessage("alert.success", null,
        LocaleContextHolder.getLocale());
    String failed = messageSource.getMessage("alert.failed", null,
        LocaleContextHolder.getLocale());
    log.debug("LocaleContextHolder.getLocale(): {}", LocaleContextHolder.getLocale());
    return Set.of(success, failed);
  }

  @GetMapping("/errors")
  public Map<String, String> test404ErrorMessage() {
    String key404 = "error.404";
    String key500 = "error.500";

    log.debug("LocaleContextHolder.getLocale(): {}", LocaleContextHolder.getLocale());
    return Map.of(key404, messageSource.getMessage(key404, null, LocaleContextHolder.getLocale())
        , key500, messageSource.getMessage(key500, null, LocaleContextHolder.getLocale()));
  }
}
