package com.prod.services;

import com.prod.config.PROMPT;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final ChatClient openAiChatClient;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;

    public String askAiRAG(String text) {

//        List<Document> documents= similaritySearch(text);
//        String context = documents.stream()
//                .map(doc->doc.getText())
//                .collect(Collectors.joining("\n"));

//        promptTemplate.add("question",text);
//        promptTemplate.add("context",context);

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template(PROMPT.Template)
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .variables(Map.of("question_answer_context",text))
                .build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(vectorMovie())
                .promptTemplate(promptTemplate)
                .build();
        String conversationId ="suraj";
        return openAiChatClient.prompt(text)

                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                        .build(),
                        SimpleLoggerAdvisor.builder().build(),
                        qaAdvisor

                )
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

    private SearchRequest vectorMovie(){
        return SearchRequest.builder()
                        .topK(4)
                        .similarityThreshold(0.3)
                .filterExpression("type == 'movie'")
                        .build();

    }
}
