package com.SpringAI.AiPractice.Controller;

import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private GoogleGenAiChatModel chatModel;
    public TestController(GoogleGenAiChatModel chatModel){
        this.chatModel=chatModel;
    }
    @GetMapping("/simplemessage/{message}")
    public String SimpleMessage(@PathVariable String message){

        String response= chatModel.call(message);
        return "Working"+ response;
    }
}
