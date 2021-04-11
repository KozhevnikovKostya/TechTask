package com.kozhevnikov.TechTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableAsync
public class TechTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechTaskApplication.class, args);
	}

}
