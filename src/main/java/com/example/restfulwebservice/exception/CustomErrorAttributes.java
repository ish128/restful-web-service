package com.example.restfulwebservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  private final ObjectMapper objectMapper;

  public CustomErrorAttributes(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest,
      ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
    errorAttributes.put("locale", webRequest.getLocale()
        .toString());
    errorAttributes.remove("error");
    errorAttributes.put("cause", errorAttributes.get("message"));
    errorAttributes.remove("message");
    errorAttributes.put("status", String.valueOf(errorAttributes.get("status")));
    return errorAttributes;
  }
}
