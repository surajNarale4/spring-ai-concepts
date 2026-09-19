package com.prod;

import com.prod.clients.Student;
import com.prod.clients.StudentClient;
import com.prod.config.RestClientConfig;
import com.prod.entities.User;
import com.prod.services.AiService;
import com.prod.services.JwtService;
import com.prod.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootTest
class ProdApplicationTests {
	@Autowired
	private AiService aiService;

	@Qualifier("openAiEmbeddingModel")
	private EmbeddingModel embeddingModel;

	@Autowired
	private VectorStore vectorStore;





}
