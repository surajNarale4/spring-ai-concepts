package com.prod.controller;


import com.openai.models.embeddings.EmbeddingModel;
import com.prod.dto.AiRequest;
import com.prod.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RequestMapping(path= "api")
@RequiredArgsConstructor
@RestController
public class AiController {

    private final AiService aiService;


    @PostMapping(value = "/ai",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> message(@RequestBody AiRequest aiRequest , @RequestParam("message") String message){
        return aiService.getJokeBySteam(message);
    }

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
            EmbeddingResponse embeddingResponse =  aiService.getEmbedding(message);
        return Map.of("embedding", embeddingResponse,"vector",embeddingResponse.getResult().getOutput());
    }

    @GetMapping("/bi")
    public String message1(){
        return "Hello";
    }
}
