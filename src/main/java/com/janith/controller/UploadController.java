package com.janith.controller;

import com.janith.Service.CloudinaryService;
import com.janith.Service.UserService;
import com.janith.Service.ShopService;
import com.janith.model.Shop;
import com.janith.model.User;
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
    private final UserService userService;
    private final ShopService shopService;

    public UploadController(CloudinaryService cloudinaryService, UserService userService, ShopService shopService) {
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.shopService = shopService;
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadProduct(@RequestParam("file") MultipartFile file) {
        return handleUpload(file, "products");
    }

    @PostMapping(value = "/shop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadShop(@RequestHeader(value = "Authorization", required = false) String jwt,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "shopId", required = false) Long shopId) {
        Map<String, Object> response = new HashMap<>();
        try {
            String url = cloudinaryService.uploadImage(file, "shops");

            // If we have auth, try to persist into a shop
            if (jwt != null && !jwt.isBlank()) {
                try {
                    User user = userService.findUserByJwtToken(jwt);
                    Shop target;
                    if (shopId != null) {
                        target = shopService.findShopById(shopId);
                        if (target.getOwner() == null || !target.getOwner().getId().equals(user.getId())) {
                            response.put("success", false);
                            response.put("url", null);
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                        }
                    } else {
                        // infer the caller's shop
                        target = shopService.getShopByUserId(user.getId());
                    }
                    shopService.addShopImage(target.getId(), url);
                } catch (Exception e) {
                    // If persisting fails, still return the URL but indicate failure status
                    response.put("success", false);
                    response.put("url", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

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

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadProfile(@RequestHeader("Authorization") String jwt,
                                                             @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            String url = cloudinaryService.uploadImage(file, "profiles");
            User user = userService.findUserByJwtToken(jwt);
            userService.updateProfileImage(user.getId(), url);

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
        } catch (Exception e) {
            response.put("success", false);
            response.put("url", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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
