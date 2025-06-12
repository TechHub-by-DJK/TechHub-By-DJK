package com.janith.controller;

import com.janith.Service.TechGadgetService;
import com.janith.model.TechGadget;
import com.janith.request.CreateTechGadgetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/techgadget")
public class TechGadgetController {
    @Autowired
    private TechGadgetService techGadgetService;

    @PostMapping
    public ResponseEntity<TechGadget> createTechGadget(@RequestBody CreateTechGadgetRequest request) {
        TechGadget gadget = techGadgetService.createTechGadget(request);
        return new ResponseEntity<>(gadget, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechGadget> updateTechGadget(@PathVariable Long id, @RequestBody CreateTechGadgetRequest request) {
        TechGadget gadget = techGadgetService.updateTechGadget(id, request);
        if (gadget == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gadget, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechGadget(@PathVariable Long id) {
        techGadgetService.deleteTechGadget(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechGadget> getTechGadgetById(@PathVariable Long id) {
        TechGadget gadget = techGadgetService.getTechGadgetById(id);
        if (gadget == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gadget, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TechGadget>> getAllTechGadgets() {
        return new ResponseEntity<>(techGadgetService.getAllTechGadgets(), HttpStatus.OK);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<TechGadget>> getTechGadgetsByShop(@PathVariable Long shopId) {
        return new ResponseEntity<>(techGadgetService.getTechGadgetsByShop(shopId), HttpStatus.OK);
    }

    @GetMapping("/compatibility/{type}")
    public ResponseEntity<List<TechGadget>> getTechGadgetsByCompatibility(@PathVariable String type) {
        return new ResponseEntity<>(techGadgetService.getTechGadgetsByCompatibility(type), HttpStatus.OK);
    }
}

