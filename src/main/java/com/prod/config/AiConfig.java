package com.prod.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

@Configuration
public class AiConfig {


    @Bean("ollamaChatClient")
    public ChatClient ollama(@Qualifier("ollamaChatModel") ChatModel ollamaChatModel){
        return ChatClient.builder(ollamaChatModel).build();
    }

    @Bean("openAiChatClient")
    public ChatClient openAi(@Qualifier("openAiChatModel") ChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel).build();
    }


    @Bean
    @Primary
    public EmbeddingModel primaryEmbeddingModel(
            @Qualifier("openAiEmbeddingModel")
            EmbeddingModel openAiEmbeddingModel) {

        return openAiEmbeddingModel;
    }




}
