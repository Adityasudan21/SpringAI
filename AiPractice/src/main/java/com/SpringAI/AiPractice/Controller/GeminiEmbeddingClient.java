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
    @PostMapping("/api/embedding/similarity")
    public double getSimilarity(@RequestParam String text1, @RequestParam String text2){

        // Get Embeddings array of both the text

        Content content1 = Content.builder()
                .parts(Collections.singletonList(
                        Part.builder().text(text1).build()
                ))
                .build();

        Content content2 = Content.builder()
                .parts(Collections.singletonList(
                        Part.builder().text(text2).build()
                ))
                .build();
        // Configure the shortened dimensionality (e.g., 512 dimensions)
        EmbedContentConfig config = EmbedContentConfig.builder()
                .outputDimensionality(512)
                .build();

        // Call the Gemini API
        EmbedContentResponse response1 = client.models.embedContent(
                "gemini-embedding-2",
                content1,
                config
        );

        EmbedContentResponse response2 = client.models.embedContent(
                "gemini-embedding-2",
                content2,
                config
        );

        // Get result embeddings array
        ContentEmbedding embedding1 = response1.embeddings().orElseThrow().get(0);
        List<Float> values1 = embedding1.values().orElseThrow();
        float[] result1 = new float[values1.size()];
        for (int i = 0; i < values1.size(); i++) {
            result1[i] = values1.get(i);
        }

        ContentEmbedding embedding2 = response2.embeddings().orElseThrow().get(0);
        List<Float> values2 = embedding2.values().orElseThrow();
        float[] result2 = new float[values2.size()];
        for (int i = 0; i < values2.size(); i++) {
            result2[i] = values2.get(i);
        }

        // We need to use formula here ourself as java does no give inbuilt formula for this like numpy in python
        double dotProduct=0;
        double norm1=0;
        double norm2=0;

        for(int i=0;i<result1.length;i++){
            dotProduct+=result1[i]*result2[i];
            norm1+=Math.pow(result1[i],2);
            norm2+=Math.pow(result2[i],2);
        }
        double value= dotProduct/(Math.sqrt(norm1)*Math.sqrt(norm2));

        //percentage of similarity
        return value*100;
    }
}
