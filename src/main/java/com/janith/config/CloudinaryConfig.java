package com.janith.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CloudinaryProperties cloudinaryProperties() {
        // Support CLOUDINARY_URL if provided: cloudinary://<api_key>:<api_secret>@<cloud_name>
        String url = System.getenv("CLOUDINARY_URL");
        if (url != null && url.startsWith("cloudinary://")) {
            try {
                String noScheme = url.substring("cloudinary://".length());
                String[] parts = noScheme.split("@");
                String cred = parts[0];
                String cn = parts[1];
                String[] credParts = cred.split(":", 2);
                String key = credParts[0];
                String secret = credParts.length > 1 ? credParts[1] : "";
                return new CloudinaryProperties(cn, key, secret);
            } catch (Exception ignored) { }
        }
        return new CloudinaryProperties(cloudName, apiKey, apiSecret);
    }

    public record CloudinaryProperties(String cloudName, String apiKey, String apiSecret) {}
}
