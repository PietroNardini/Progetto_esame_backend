package com.backend.Backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServizioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServizioApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		/*Questo metodo stampa un avviso quando il server si avvia */
		return args -> {
			System.out.println("Server is listening...");
		};
	}
}
