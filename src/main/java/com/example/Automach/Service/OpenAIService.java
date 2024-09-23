package com.example.Automach.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    public String getChatbotResponse(String userInput) {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Prepare request body
        String requestBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + userInput + "\"}]"
                + "}";

        // Create the request entity
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Make the POST request
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());

                // Extract the chatbot's reply from the response
                String botReply = jsonResponse
                        .path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText();

                return botReply;
            } else {
                return "Error: " + response.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong: " + e.getMessage();
        }
    }
}
