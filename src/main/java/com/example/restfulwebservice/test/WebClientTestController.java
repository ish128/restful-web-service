package com.example.restfulwebservice.test;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The type Web client test controller.
 * webClient 사용하기 위해서 spring-boot-starter-webflux 필요.
 */
@RequestMapping("/webclient")
@AllArgsConstructor
@RestController
public class WebClientTestController {
    private final WebClient webClient;

    @GetMapping("/test")
    public Mono<String> doTest() {
        return webClient.get()
                .uri("http://localhost:8080/webserver/test")
                .retrieve()
                .bodyToMono(String.class);
    }
}
