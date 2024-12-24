package com.janith.controller;

import com.janith.Service.ShopService;
import com.janith.Service.UserService;
import com.janith.dto.ShopDto;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.request.CreateShopRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<Shop>> searchShop(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Shop> shop = shopService.searchShop(keyword);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Shop>> getAllShop(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Shop> shop = shopService.getAllShops();
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> findShopById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.findShopById(id);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<ShopDto> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        ShopDto shop = shopService.addToFavourites(id, user);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }


}
