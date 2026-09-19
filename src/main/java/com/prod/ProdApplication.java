package com.prod;

import com.prod.services.AiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication

public class ProdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(AiService aiService){
//
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				aiService.engestDocument("Hello Suraj");
//			}
//		};
//	}
}
