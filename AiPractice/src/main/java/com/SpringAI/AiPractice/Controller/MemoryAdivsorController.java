package com.SpringAI.AiPractice.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "http://localhost:5173")
@RestController
public class MemoryAdivsorController {
    private ChatClient chatClient;

    ChatMemory chatMemory= MessageWindowChatMemory.builder().build();

    public MemoryAdivsorController(ChatClient.Builder builder){
        this.chatClient=builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }
    @GetMapping("/memorymessage/{message}")
    public String MemoryMessage(@PathVariable String message){
        ChatResponse chatResponse=chatClient.prompt(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "default-conversation")) //This is added as there is an conversation ID associated with the session you are using
                .call()
                .chatResponse();
        String response= chatResponse
                .getResult()
                .getOutput()
                .getText();
        return response;
    }
}
