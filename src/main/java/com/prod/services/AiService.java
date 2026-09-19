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
                                "director", "Christopher Nolan"
                        )
                ),
                new Document(
                        "Inception",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2010,
                                "director", "Christopher Nolan"
                        )
                ),
                new Document(
                        "Interstellar",
                        Map.of(
                                "genre", "Sci-Fi",
                                "year", 2014,
                                "director", "Christopher Nolan"
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
                        .topK(2)
                        .similarityThreshold(0.7)
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

    public String askAiRAG(String text){

        String template = """
    You are a movie recommendation system.
    and your name is :'suraj ai system'
    Use the following movie information to answer the user's question.

    Context:
    '{context}'

    Recommend a movie should based on the context only.
   
""";


        List<Document> documents= similaritySearch(text);
        String context = documents.stream()
                .map(doc->doc.getText())
                .collect(Collectors.joining("\n"));

//        promptTemplate.add("question",text);
//        promptTemplate.add("context",context);


        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("context",context));
        System.out.println("System Promp :"+systemPrompt);

        return openAiChatClient
                .prompt()
                .system(systemPrompt)
                .user(text)
                .call()
                .content();
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
