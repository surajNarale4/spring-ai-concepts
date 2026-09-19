package com.prod.services;




import com.prod.dto.AiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j

public class AiService {

    private final ChatClient ollamaChatClient;

    private final OpenAiEmbeddingModel embeddingModel;
    private final VectorStore vectorStore;


    private final ChatClient openAiChatClient;


    public void engestDocument(String text){
        Document document = new Document(text);
        vectorStore.add(List.of(document));

    }

    public AiService(@Qualifier("openAiChatClient") ChatClient openAiChatClient,
                     @Qualifier("ollamaChatClient") ChatClient ollamaChatClient,
                     @Qualifier("openAiEmbeddingModel") OpenAiEmbeddingModel embeddingModel, VectorStore vectorStore){
        this.ollamaChatClient=ollamaChatClient;
        this.openAiChatClient=openAiChatClient;
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
    }

    public String getJoke(String topic){
        return openAiChatClient
                .prompt(topic)
                .call()
                .chatClientResponse().chatResponse().getResult().toString();
    }


    public String message(AiRequest aiRequest,String message) {
        if(message!=null){
            aiRequest.setMessage(message);
        }
        return openAiChatClient
                .prompt(aiRequest.getMessage())
                .call()
                .chatClientResponse()
                .chatResponse()
                .toString();

    }

    public Flux<?> getJokeBySteam(String topic){

        return  openAiChatClient
                .prompt(topic)
                .stream()
                .content()
                .doOnNext(c-> log.info("chunk {}",c))
                .doOnComplete(()->log.info("stream completed..."));

           }

    public EmbeddingResponse getEmbedding(String message) {

        return embeddingModel.embedForResponse(List.of(message));
    }
}
