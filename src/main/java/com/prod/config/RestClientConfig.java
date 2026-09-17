package com.prod.config;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Configuration
public class RestClientConfig {



  //  private static final String APPCLICATION_JSON_VALUE = ;
    @Value("${fast.api.base.url}")
    private String baseUrl;



    @Bean
    @Qualifier("fatsApi")
    RestClient getHelloWordFromfast() {
      return RestClient.builder()
              .baseUrl(baseUrl)
              .requestFactory(new SimpleClientHttpRequestFactory())
              .build();
    }
}
