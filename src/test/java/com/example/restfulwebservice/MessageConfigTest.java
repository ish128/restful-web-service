package com.example.restfulwebservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@Slf4j
@SpringBootTest
public class MessageConfigTest {

  @Autowired
  private MessageSource messageSource;

  @Test
  void notFoundMessageCode() {
    assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
        .isInstanceOf(NoSuchMessageException.class);
  }

  @Test
  void testLocale_ko() {
    String error404 = messageSource.getMessage("error.404", null, Locale.KOREAN);

    log.debug(Locale.KOREAN.toString());
    log.debug("error404: {}", error404);
  }

  @Test
  void testLocale_ko_KR() {
    String error404 = messageSource.getMessage("error.404", null, Locale.KOREA);

    log.debug(Locale.KOREA.toString());
    log.debug("error404: {}", error404);
  }

  @Test
  void testLocale_null() {
    String error404 = messageSource.getMessage("error.404", null, null);
    log.debug("error404: {}", error404);
  }


  @Test
  void testLocale_en() {
    String error404 = messageSource.getMessage("error.404", null, Locale.ENGLISH);
    log.debug("error404: {}", error404);
  }

  @Test
  void notFoundMessageCodeDefaultMessage() {
    String result = messageSource.getMessage("no_code", null, "기본 메시지", null);
    assertThat(result).isEqualTo("기본 메시지");
  }


}
