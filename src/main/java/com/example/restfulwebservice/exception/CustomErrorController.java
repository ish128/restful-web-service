package com.example.restfulwebservice.exception;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The type Custom error controller.
 */
@Component
public class CustomErrorController extends BasicErrorController {

  /**
   * Instantiates a new Custom error controller.
   * <p> Spring Framework 4.3부터 @Autowired대상 bean이 시작할 생성자를 하나만 정의한다면 @Autowired 생략 가능 </p>
   *
   * @param errorAttributes  the error attributes
   * @param serverProperties the server properties
   */
  public CustomErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
    super(errorAttributes, serverProperties.getError());
  }

  @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Map<String, Object>> xmlError(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request,
        getErrorAttributeOptions(request, MediaType.APPLICATION_XML));
    body.put("xmlkey", "the XML response is different!");
    HttpStatus status = getStatus(request);
    return new ResponseEntity<>(body, status);
  }
}
