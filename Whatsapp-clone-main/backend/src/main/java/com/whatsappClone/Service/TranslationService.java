package com.whatsappClone.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    @Value("${deepl.api.key}")
    private String apiKey;

    @Value("${deepl.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String translateText(String text, String targetLanguage) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        if (targetLanguage == null || targetLanguage.trim().isEmpty()) {
            return text;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String requestBody = "auth_key=" + apiKey +
                               "&text=" + java.net.URLEncoder.encode(text, "UTF-8") +
                               "&target_lang=" + targetLanguage.toUpperCase();

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("translations")) {
                    java.util.List<Map<String, Object>> translations =
                        (java.util.List<Map<String, Object>>) responseBody.get("translations");
                    if (!translations.isEmpty()) {
                        Map<String, Object> translation = translations.get(0);
                        return (String) translation.get("text");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Translation failed: " + e.getMessage());
            // Return original text if translation fails
        }

        return text;
    }

    public String detectLanguage(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "EN"; // Default to English
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String requestBody = "auth_key=" + apiKey +
                               "&text=" + java.net.URLEncoder.encode(text, "UTF-8");

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl.replace("/translate", "/detect"), entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("detections")) {
                    java.util.List<Map<String, Object>> detections =
                        (java.util.List<Map<String, Object>>) responseBody.get("detections");
                    if (!detections.isEmpty()) {
                        Map<String, Object> detection = detections.get(0);
                        return (String) detection.get("language");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Language detection failed: " + e.getMessage());
        }

        return "EN"; // Default to English if detection fails
    }
}
