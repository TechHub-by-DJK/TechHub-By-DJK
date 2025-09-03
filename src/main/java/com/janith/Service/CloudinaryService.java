package com.janith.Service;

import com.janith.config.CloudinaryConfig.CloudinaryProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class CloudinaryService {

    private final RestTemplate restTemplate;
    private final CloudinaryProperties props;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/jpg"
    );

    public CloudinaryService(RestTemplate restTemplate, CloudinaryProperties props) {
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = Optional.ofNullable(file.getContentType()).orElse("").toLowerCase(Locale.ROOT);
        boolean typeOk = ALLOWED_TYPES.contains(contentType);
        if (!typeOk) {
            String name = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase(Locale.ROOT);
            typeOk = name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
        }
        if (!typeOk) {
            throw new IllegalArgumentException("Invalid file type. Only jpg, jpeg, png are allowed.");
        }

        // Params
        String publicId = UUID.randomUUID().toString();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String overwrite = "true";

        Map<String, String> toSign = new TreeMap<>();
        toSign.put("folder", folder);
        toSign.put("overwrite", overwrite);
        toSign.put("public_id", publicId);
        toSign.put("timestamp", timestamp);

        String signature = signParams(toSign, props.apiSecret());

        // Build multipart form
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", asFilePart(file));
        body.add("api_key", props.apiKey());
        body.add("timestamp", timestamp);
        body.add("folder", folder);
        body.add("public_id", publicId);
        body.add("overwrite", overwrite);
        body.add("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String endpoint = String.format("https://api.cloudinary.com/v1_1/%s/image/upload", props.cloudName());
        ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(endpoint, requestEntity, (Class<Map<String, Object>>)(Class<?>)Map.class);

        Map<String, Object> res = response.getBody();
        if (res == null) throw new IOException("Empty response from Cloudinary");
        String url = null;
        Object secure = res.get("secure_url");
        if (secure instanceof String) {
            url = (String) secure;
        } else {
            Object normal = res.get("url");
            if (normal instanceof String) url = (String) normal;
        }
        if (url == null) throw new IOException("Upload failed: no URL returned");
        return url;
    }

    private static String signParams(Map<String, String> params, String apiSecret) {
        // Build 'key=value' joined by '&' in key-sorted order
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (!first) sb.append('&');
            first = false;
            sb.append(e.getKey()).append('=').append(e.getValue());
        }
        sb.append(apiSecret);
        return sha1Hex(sb.toString());
    }

    private static String sha1Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                String h = Integer.toHexString(b & 0xff);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-1 not available", e);
        }
    }

    private static ByteArrayResource asFilePart(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("upload");
        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return filename;
            }

            @Override
            public long contentLength() {
                return bytes.length;
            }
        };
    }
}
