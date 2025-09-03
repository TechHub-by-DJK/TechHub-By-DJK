package com.janith.controller;

import com.janith.Service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    private final CloudinaryService cloudinaryService;

    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadProduct(@RequestParam("file") MultipartFile file) {
        return handleUpload(file, "products");
    }

    @PostMapping(value = "/shop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadShop(@RequestParam("file") MultipartFile file) {
        return handleUpload(file, "shops");
    }

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadProfile(@RequestParam("file") MultipartFile file) {
        return handleUpload(file, "profiles");
    }

    private ResponseEntity<Map<String, Object>> handleUpload(MultipartFile file, String folder) {
        Map<String, Object> response = new HashMap<>();
        try {
            String url = cloudinaryService.uploadImage(file, folder);
            response.put("success", true);
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("url", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            response.put("success", false);
            response.put("url", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

