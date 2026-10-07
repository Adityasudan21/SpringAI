package com.SpringAI.AiPractice.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    private ChatClient chatClient;

    // Here we will inject the LLM provider of are type as ChatModel
    public GeneralController(GoogleGenAiChatModel chatModel){
        this.chatClient=ChatClient.create(chatModel); // This create the Chatclient from ChatModel
    }
    @GetMapping("/apicall/chatclient/{message}")
    public String getChatClientCall(@PathVariable String message){
        //Using Chatclient to get response
        String response= chatClient
                .prompt(message) //Prompt
                .call() //Call API
                .content(); //Give only content and not metadata

        return response;
    }

    //
    @GetMapping("/apicall/chatresponse/{message}")
    public String getChatResponseCall(@PathVariable String message){

        //Using chatResponse to get chatresponse Object
        ChatResponse chatResponse= chatClient
                .prompt(message) //Prompt
                .call() //Call API
                .chatResponse();

        //This will be printed in Console
        System.out.println(chatResponse.getMetadata().getModel());

        //Get response of API in text
        String response=chatResponse
                .getResult()
                .getOutput()
                .getText();

        return response;
    }
}
