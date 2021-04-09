package com.kozhevnikov.TechTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class TechTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechTaskApplication.class, args);
	}

}
