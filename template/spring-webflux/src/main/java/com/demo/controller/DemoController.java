package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        Flux<Integer> flux = Flux.range(1, 3)
                .doOnNext(i -> System.out.println("Chuẩn bị emit: " + i))
                .map(i -> i * 10);

        flux.subscribe(System.out::println); // Log và in kết quả

        Flux<Integer> flux2 = Flux.range(1, 3)
                .map(i -> {
                    if (i == 2) throw new RuntimeException("Lỗi tại 2");
                    return i;
                })
                .onErrorResume(e -> Flux.just(-1, -2)); // fallback

        flux2.subscribe(System.out::println); // In: 1, -1, -2

        return Mono.just("Hello from WebFlux!");
    }

    @GetMapping("/numbers")
    public Flux<Integer> getNumbers() {
        return Flux.range(1, 5).delayElements(Duration.ofSeconds(1));
    }
}
