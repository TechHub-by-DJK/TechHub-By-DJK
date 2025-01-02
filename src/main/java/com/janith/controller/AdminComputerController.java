package com.janith.controller;

import com.janith.Service.ComputerService;
import com.janith.Service.ShopService;
import com.janith.Service.UserService;
import com.janith.model.Computer;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.request.CreateComputerRequest;
import com.janith.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/computer")
public class AdminComputerController {

    @Autowired
    private ComputerService computerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<Computer> createComputer(@RequestBody CreateComputerRequest req,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Shop shop = shopService.findShopById(req.getShopId());
        Computer computer = computerService.createComputer(req, req.getCategory(), shop);

        return new ResponseEntity<>(computer, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComputer(@PathVariable Long id,
                                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        computerService.deleteComputer(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("Successfully deleted computer");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Computer> updateComputerAvailabilityComputer(@PathVariable Long id,
                                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Computer computer = computerService.updateAvailabilityStatus(id);


        return new ResponseEntity<>(computer, HttpStatus.OK);
    }
}

