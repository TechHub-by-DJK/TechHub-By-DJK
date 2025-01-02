package com.janith.controller;


import com.janith.Service.ComputerService;
import com.janith.Service.ShopService;
import com.janith.Service.UserService;
import com.janith.model.Computer;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.request.CreateComputerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/computer")
public class ComputerController {
    @Autowired
    private ComputerService computerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @GetMapping("/search")
    public ResponseEntity<List<Computer>> searchComputer(@RequestParam String name,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Computer> computers = computerService.searchComputer(name);

        return new ResponseEntity<>(computers, HttpStatus.CREATED);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<Computer>> getShopComputer(
            @RequestParam boolean gamer,
            @RequestParam boolean designer,
            @RequestParam boolean developer,
            @RequestParam boolean homeUser,
            @RequestParam boolean bussinessPerson,
            @RequestParam boolean seasonal,
            @RequestParam(required = false) String computer_category,
            @PathVariable Long shopId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Computer> computers = computerService.getShopComputers(shopId, gamer, designer, developer, homeUser, bussinessPerson, seasonal, computer_category);

        return new ResponseEntity<>(computers, HttpStatus.OK);
    }


}
