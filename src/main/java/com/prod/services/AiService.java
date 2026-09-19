package com.prod.services;




import com.prod.dto.AiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j

public class AiService {

    private final ChatClient ollamaChatClient;

    private final OpenAiEmbeddingModel embeddingModel;
    private final VectorStore vectorStore;


    private final ChatClient openAiChatClient;


    public void engestDocument(){
        List<Document> movies = List.of(
                new Document(
                        "The Dark Knight",
                        Map.of(
                                "genre", "Action",
                                "year", 2008,
                                "director", "Christopher Nolan",
                                "type", "movie"
                        )
                ),
                new Document(
                        "Inception",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2010,
                                "director", "Christopher Nolan",
                                "type", "movie"
                        )
                ),
                new Document(
                        "Interstellar",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2014,
                                "director", "Christopher Nolan",
                                "type", "movie"
                        )
                ),
                new Document(
                        "The Matrix",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 1999,
                                "director", "The Wachowskis",
                                "type", "movie"
                        )
                ),
                new Document(
                        "Avengers: Endgame",
                        Map.of(
                                "genre", "Action",
                                "year", 2019,
                                "director", "Anthony Russo, Joe Russo",
                                "type", "movie"
                        )
                ),
                new Document(
                        "The Shawshank Redemption",
                        Map.of(
                                "genre", "Drama",
                                "year", 1994,
                                "director", "Frank Darabont",
                                "type", "movie"
                        )
                ),
                new Document(
                        "Breaking Bad",
                        Map.of(
                                "genre", "Crime",
                                "year", 2008,
                                "director", "Vince Gilligan",
                                "type", "webseries"
                        )
                ),
                new Document(
                        "Stranger Things",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2016,
                                "director", "The Duffer Brothers",
                                "type", "webseries"
                        )
                ),
                new Document(
                        "Dark",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2017,
                                "director", "Baran bo Odar",
                                "type", "webseries"
                        )
                ),
                new Document(
                        "The Boys",
                        Map.of(
                                "genre", "Action",
                                "year", 2019,
                                "director", "Eric Kripke",
                                "type", "webseries"
                        )
                ),
                new Document(
                        "Death Note",
                        Map.of(
                                "genre", "Psychological Thriller",
                                "year", 2006,
                                "director", "Tetsuro Araki",
                                "type", "anime"
                        )
                ),
                new Document(
                        "Attack on Titan",
                        Map.of(
                                "genre", "Action",
                                "year", 2013,
                                "director", "Tetsuro Araki",
                                "type", "anime"
                        )
                ),
                new Document(
                        "Demon Slayer",
                        Map.of(
                                "genre", "Action",
                                "year", 2019,
                                "director", "Haruo Sotozaki",
                                "type", "anime"
                        )
                ),
                new Document(
                        "One Piece",
                        Map.of(
                                "genre", "Adventure",
                                "year", 1999,
                                "director", "Konosuke Uda",
                                "type", "anime"
                        )
                ),
                new Document(
                        "Naruto",
                        Map.of(
                                "genre", "Action",
                                "year", 2002,
                                "director", "Hayato Date",
                                "type", "anime"
                        )
                )
        );


        vectorStore.add(movies);

    }

    public AiService(@Qualifier("openAiChatClient") ChatClient openAiChatClient,
                     @Qualifier("ollamaChatClient") ChatClient ollamaChatClient,
                     @Qualifier("openAiEmbeddingModel") OpenAiEmbeddingModel embeddingModel, VectorStore vectorStore){
        this.ollamaChatClient=ollamaChatClient;
        this.openAiChatClient=openAiChatClient;
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
    }

    public List<Document> similaritySearch(String text){
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(text)
                        .topK(4)
                        .similarityThreshold(0.3)
                        /*
                        we can add expression
                        if we vector data for spring ai
                        and some movies besed metadata we can make filter out
                        our vectors get documents
                         */
//                        .filterExpression(b.and(
//                                b.in("author","john", "jill"),
//                                b.eq("article_type", "blog")).build())
                        .build()

        );
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
