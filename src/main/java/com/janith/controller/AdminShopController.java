package com.janith.controller;

import com.janith.Service.ShopService;
import com.janith.Service.UserService;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.request.CreateShopRequest;
import com.janith.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/shop")
public class AdminShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<Shop> createShop(
            @RequestBody CreateShopRequest req,
            @RequestHeader ("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Shop shop = shopService.createShop(req, user);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(
            @RequestBody CreateShopRequest req,
            @RequestHeader ("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.updateShop(id, req);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteShop(
            @RequestHeader ("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        shopService.deleteShop(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Shop deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Shop> updateShopStatus(
            @RequestHeader ("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Shop shop = shopService.updateShopStatus(id);

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Shop> findShopByUserId(
            @RequestHeader ("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Shop shop = shopService.getShopByUserId(user.getId());

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }



}
