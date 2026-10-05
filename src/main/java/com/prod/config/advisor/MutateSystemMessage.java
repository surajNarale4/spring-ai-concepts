package com.prod.config.advisor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class MutateSystemMessage implements StreamAdvisor {

    @NotNull
    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
       log.info("chat client request {}",chatClientRequest);
        List<Message> messages = new ArrayList<>();
        SystemMessage systemMessage = new SystemMessage("Your name is krishna ");
        Prompt oldPromp = chatClientRequest.prompt();
        messages.add(systemMessage);
        for(Message message : oldPromp.getInstructions()){
            if(!(message instanceof SystemMessage))
                messages.add(message);
        }
       Prompt prompt = new Prompt(messages,oldPromp.getOptions());
       ChatClientRequest newChatClientRequest = new ChatClientRequest(prompt,chatClientRequest.context());
        return streamAdvisorChain.nextStream(newChatClientRequest);
    }

    @Override
    public String getName() {
        return "muate ";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
