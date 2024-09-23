
package com.example.Automach.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin
public class ChatbotController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @PostMapping("/api/chatbot")
    public ResponseEntity<String> askChatbot(@RequestBody String userInput) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);

        String requestBody = "{"
                + "\"model\":\"gpt-3.5-turbo\","
                + "\"messages\":[{\"role\":\"user\",\"content\":\"" + userInput + "\"}],"
                + "\"max_tokens\":150"
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
    }
}
