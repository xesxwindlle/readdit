package com.readdit.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readdit.service.OpenRouterChatService;

@RestController
@RequestMapping("/api/chat")
public class OpenRouterChatController {

    private final OpenRouterChatService chatService;
    private final ObjectMapper objectMapper;

    public OpenRouterChatController(OpenRouterChatService chatService,
            ObjectMapper objectMapper) {
        this.chatService = chatService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> req) {

        String prompt = req.get("prompt");
        String raw = chatService.chat(prompt);

        try {
            JsonNode node = objectMapper.readTree(raw);

            String content = node
                    .path("choices").path(0)
                    .path("message").path("content")
                    .asText(null);

            if (content != null && !content.isBlank()) {
                return ResponseEntity.ok(Map.of(
                        "content", content,
                        "parsed", true));
            }

        } catch (Exception ignore) {
        }

        // fallback：LLM 沒照 schema，直接當純文字
        return ResponseEntity.ok(Map.of(
                "content", raw,
                "parsed", false));
    }
}