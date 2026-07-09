package br.udesc.edu.demandatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class DemandaTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemandaTechApplication.class, args);
    }

}
