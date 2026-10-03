package com.SpringAI.AiPractice.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatBuilderController {
    private ChatClient chatClient;

    //Using Chat Bulider in ChatClient to automatically detect LLM Provider
    public ChatBuilderController(ChatClient.Builder builder){
        this.chatClient=builder.build();
    }

    @GetMapping("/apicall/chatbuilder/{message}")
    public ResponseEntity<String> getChatBuilderCall(@PathVariable String message){

        ChatResponse chatResponse= chatClient.prompt(message).call().chatResponse();

        String response=chatResponse.getResult().getOutput().getText();
        return ResponseEntity.ok(response);
    }
}
