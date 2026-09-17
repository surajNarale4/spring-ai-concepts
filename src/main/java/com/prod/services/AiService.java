package com.prod.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service

public class AiService {

    private final ChatClient ollamaChatModel;


    private final ChatClient openAiChatModel;

    public AiService(@Qualifier("openAiChatModel") ChatClient openAiChatModel, @Qualifier("ollamaChatModel") ChatClient ollamaChatModel){
        this.ollamaChatModel=ollamaChatModel;
        this.openAiChatModel=openAiChatModel;
    }

    public String getJoke(String topic){
        return openAiChatModel
                .prompt(topic)
                .call()
                .chatClientResponse().chatResponse().getResult().toString();
    }
}
