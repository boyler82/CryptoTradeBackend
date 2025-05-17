package com.flynn.crypttrade;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition
@SpringBootApplication
@EnableScheduling
public class CryptTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptTradeApplication.class, args);
    }

}
