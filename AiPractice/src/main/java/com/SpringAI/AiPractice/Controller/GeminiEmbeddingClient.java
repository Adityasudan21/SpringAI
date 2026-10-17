package com.SpringAI.AiPractice.Controller;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.ContentEmbedding;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;
import com.google.genai.types.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class GeminiEmbeddingClient {
    Client client;
    public GeminiEmbeddingClient(){
        this.client = Client.builder().build();
    }
    @PostMapping("/api/embeddings")
    public float[] embeddings(@RequestParam String text){

        // Define the content payload
        Content content = Content.builder()
                .parts(Collections.singletonList(
                        Part.builder().text(text).build()
                ))
                .build();

        // Configure the shortened dimensionality (e.g., 512 dimensions)
        EmbedContentConfig config = EmbedContentConfig.builder()
                .outputDimensionality(4)
                .build();

        // Call the Gemini API
        EmbedContentResponse response = client.models.embedContent(
                "gemini-embedding-2",
                content,
                config
        );

        // Extract and return the float array values from the response
        ContentEmbedding embedding = response.embeddings().orElseThrow().get(0);
        List<Float> values = embedding.values().orElseThrow();
        float[] result = new float[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }
}
