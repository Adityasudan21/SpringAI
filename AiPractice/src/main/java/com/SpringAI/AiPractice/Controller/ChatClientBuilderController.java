package com.SpringAI.AiPractice.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientBuilderController {
    private ChatClient chatClient;

    //Using Chat Builder in ChatClient to automatically detect LLM Provider
    public ChatClientBuilderController(ChatClient.Builder builder){
        //This use to build ChatClient Object
        this.chatClient=builder.build();
    }

    @GetMapping("/apicall/chatclientbuilder/{message}")
    public ResponseEntity<String> getChatClientBuilderCall(@PathVariable String message){

        ChatResponse chatResponse= chatClient.prompt(message).call().chatResponse();

        String response=chatResponse.getResult().getOutput().getText();
        return ResponseEntity.ok(response);
    }
}
