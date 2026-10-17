# AiPractice Fullstack — Spring AI + Gemini

A practice project exploring Spring AI's integration with Google's Gemini models — chat
completion, conversational memory, and text embeddings (with cosine similarity search) —
backed by a Spring Boot REST API and a React (Vite) frontend.

## Tech Stack

**Backend**
- Java 21
- Spring Boot 4.1.1
- Spring AI 2.0.1 (`spring-ai-starter-model-google-genai`)
- `com.google.genai` SDK (for direct Gemini embedding calls)
- Maven

**Frontend**
- React 19
- Vite 6

## Project Structure

```
AiPractice Fullstack/
├── AiPractice/                 # Spring Boot backend
│   └── src/main/java/com/SpringAI/AiPractice/
│       ├── AiPracticeApplication.java
│       └── Controller/
│           ├── ChatClientBuilderController.java   # ChatClient.Builder autoconfigured by Spring AI
│           ├── GeminiChatModelController.java     # Raw GoogleGenAiChatModel call
│           ├── GeneralController.java             # ChatClient created manually from ChatModel
│           ├── MemoryAdivsorController.java       # Chat with conversation memory (advisor)
│           └── GeminiEmbeddingClient.java         # Text embeddings + cosine similarity search
└── AiPractice Frontend/        # React (Vite) frontend
    └── src/
```

## Backend Setup

1. Set your Gemini API key as an environment variable (required by `spring-ai-starter-model-google-genai`
   and the `com.google.genai.Client`):
   ```bash
   export GOOGLE_API_KEY=your_api_key_here
   ```
2. From the `AiPractice/` directory:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API starts on `http://localhost:8080` by default.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/apicall/chatclientbuilder/{message}` | Chat response using Spring AI's auto-configured `ChatClient.Builder` |
| GET | `/apicall/chatclient/{message}` | Chat response using a `ChatClient` built manually from `GoogleGenAiChatModel` |
| GET | `/apicall/chatresponse/{message}` | Chat response returning the full `ChatResponse` (with metadata) |
| GET | `/simplemessage/{message}` | Direct call to `GoogleGenAiChatModel` |
| GET | `/memorymessage/{message}` | Chat with conversation memory via `MessageChatMemoryAdvisor` |
| POST | `/api/embeddings?text={text}` | Returns the embedding vector (`float[]`) for the given text |
| POST | `/api/embedding/similarity?text1={text1}&text2={text2}` | Returns the cosine similarity score between the embeddings of two texts |

## Frontend Setup

From the `AiPractice Frontend/` directory:
```bash
npm install
npm run dev
```
The dev server runs on `http://localhost:5173` by default (matches the backend's
`@CrossOrigin` configuration in `MemoryAdivsorController`).

## Notes

- Embedding model used: `gemini-embedding-2`.
- Cosine similarity is computed manually (dot product over the product of vector norms) since
  Java has no built-in equivalent to NumPy's vectorized operations.
