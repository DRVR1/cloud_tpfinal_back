package com.example.adstp5back.controller;

import org.springframework.web.bind.annotation.GetMapping; // Añadido
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate; // Añadido
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*; // Añadido
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Añadido
import java.util.List; // Añadido
import java.util.Map; // Añadido

@RestController
@RequestMapping("/api")
public class AiController {

    @Value("${openrouter.api.key}")
    String API_KEY;
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    @GetMapping("/chat")
    public String askAI(String prompt) {

        // 1. El prompt que querés mandar
        System.out.println("Enviando prompt: " + prompt);

        try {
            // 2. Crear el RestTemplate (acá mismo, bien croto)
            RestTemplate restTemplate = new RestTemplate();

            // 3. Configurar Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            System.out.println("api KEY is: " + API_KEY);
            headers.setBearerAuth(API_KEY);

            // 4. Crear el Body (usando las clases internas de abajo)
            // Usamos un modelo gratuito
            Message userMessage = new Message("user", prompt);
            ChatRequest requestBody = new ChatRequest(
                    "mistralai/mistral-7b-instruct", // Modelo gratis
                    List.of(userMessage));

            // 5. Crear la Entidad HTTP
            HttpEntity<ChatRequest> entity = new HttpEntity<>(requestBody, headers);

            // 6. ¡LA LLAMADA!
            // Mapea la respuesta JSON directo a nuestra clase ChatResponse
            ResponseEntity<ChatResponse> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    ChatResponse.class);

            // 7. Sacar la respuesta (Jackson hizo todo el trabajo)
            String aiResponse = response.getBody().getChoices().get(0).getMessage().getContent();
            System.out.println(response.getBody().toString());

            // 8. Imprimir en consola
            System.out.println("--- RESPUESTA DE OPENROUTER ---");
            System.out.println(aiResponse);
            System.out.println("---------------------------------");

            // Devolver la respuesta al navegador
            return aiResponse;

        } catch (Exception e) {
            // Captura cualquier error (ej. API Key incorrecta, sin conexión)
            System.err.println("¡Error al llamar a la API! " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // --- CLASES INTERNAS (DTOs) "CROTAS" ---
    // Para no tener que crear archivos DTO separados.
    // Esto es feo pero funciona para una prueba rápida.

    // Representa el Body que ENVIAMOS
    static class ChatRequest {
        public String model;
        public List<Message> messages;

        public ChatRequest(String model, List<Message> messages) {
            this.model = model;
            this.messages = messages;
        }
    }

    // Representa el objeto "message" (ida y vuelta)
    @JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos que no mapeamos
    static class Message {
        public String role;
        public String content;

        // Constructor para enviar
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Constructor vacío para que Jackson pueda crear el objeto al RECIBIR
        public Message() {
        }

        // Getters/Setters (necesarios para Jackson, aunque los campos sean públicos)
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // Representa la Respuesta que RECIBIMOS
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChatResponse {
        public List<Choice> choices;

        // Getter/Setter
        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }
    }

    // Representa el objeto "choice" en la respuesta
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Choice {
        public Message message;

        // Getter/Setter
        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
}