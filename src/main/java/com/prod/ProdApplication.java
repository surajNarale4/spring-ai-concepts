package com.prod;

import com.prod.services.AiService;
import com.prod.services.RAGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
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

	@Bean
	public CommandLineRunner commandLineRunner(RAGService RAGService, AiService aiService){

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
//				aiService.engestDocument();
//				for(Document document : aiService.similaritySearch("what is my name ")){
//					System.out.println(document);
//				}

				String response =RAGService.askAiRAG("what is my name ??");
				System.out.println(response);

			}
		};
	}
}
