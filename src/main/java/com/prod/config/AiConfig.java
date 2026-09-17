package com.prod.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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




}
