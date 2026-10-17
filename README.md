# AiPractice — Spring AI + Gemini (Backend)

A practice project exploring Spring AI's integration with Google's Gemini models for chat
completion, backed by a Spring Boot REST API.

> **This `main` branch contains only the backend code.**
> For the full project — backend **and** frontend (React/Vite), plus additional features like
> conversational memory and text embeddings with cosine similarity search — switch to the
> **`Fullstack`** branch:
> ```bash
> git checkout Fullstack
> ```

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring AI 2.0.1 (`spring-ai-starter-model-google-genai`)
- Maven

## Project Structure

```
AiPractice/
└── src/main/java/com/SpringAI/AiPractice/
    ├── AiPracticeApplication.java
    └── Controller/
        ├── ChatBuilderController.java      # ChatClient.Builder autoconfigured by Spring AI
        ├── GeminiChatModelController.java  # Raw GoogleGenAiChatModel call
        └── GeneralController.java          # ChatClient created manually from ChatModel
```

## Setup

1. Set your Gemini API key as an environment variable (required by
   `spring-ai-starter-model-google-genai`):
   ```bash
   export GOOGLE_API_KEY=your_api_key_here
   ```
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API starts on `http://localhost:8080` by default.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/apicall/chatbuilder/{message}` | Chat response using Spring AI's auto-configured `ChatClient.Builder` |
| GET | `/apicall/chatclient/{message}` | Chat response using a `ChatClient` built manually from `GoogleGenAiChatModel` |
| GET | `/apicall/chatresponse/{message}` | Chat response returning the full `ChatResponse` (with metadata) |
| GET | `/simplemessage/{message}` | Direct call to `GoogleGenAiChatModel` |
