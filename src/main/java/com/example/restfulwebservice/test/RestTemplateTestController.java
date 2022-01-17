package com.example.restfulwebservice.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The type Rest template test controller.
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/resttemplates")
@RestController
public class RestTemplateTestController {

    /**
     * The Rest template.
     */
    private final RestTemplate restTemplate;
    /**
     * The Mapper.
     */
    private final ObjectMapper mapper;

    /**
     * Test 1 response entity.
     *
     * @return the response entity
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping("/test1")
    public ResponseEntity<String> test1() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://jsonplaceholder.typicode.com/posts/{id}/comments", String.class, "1");
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.get(0).path("name");
        log.debug(name.asText());
        return response;
    }

    /**
     * Test 2 http headers.
     *
     * @return the http headers
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping("/test2")
    public HttpHeaders test2() throws JsonProcessingException {
        HttpHeaders headers = restTemplate.headForHeaders(
                "https://jsonplaceholder.typicode.com/posts/{id}/comments", String.class, "1");
        log.debug(headers.getContentType().toString());
        return headers;
    }

    /**
     * Test 3 response entity.
     *
     * @return the response entity
     */
    @GetMapping("/test3")
    public ResponseEntity test3() {
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url + "?" + "key=key&targetDt=20210201").build();

        ResponseEntity<Object> exchange = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Object.class);

        HttpStatus statusCode = exchange.getStatusCode();   //상태코드확인
        HttpHeaders headers = exchange.getHeaders();    //헤더정보확인
        Object body = exchange.getBody();               //바디정보확인

        return exchange;
    }


}
