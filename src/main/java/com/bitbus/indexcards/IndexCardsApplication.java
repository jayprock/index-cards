package com.bitbus.indexcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class IndexCardsApplication {

    @Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }

    public static void main(String[] args) {
        SpringApplication.run(IndexCardsApplication.class, args);
    }

}
