package com.example.entorno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Entorno {

	public static void main(String[] args) {
		SpringApplication.run(Entorno.class, args);
	}
        
        @Bean
        public RestTemplate RestTemplate() {
            return new RestTemplate();
        }

}
