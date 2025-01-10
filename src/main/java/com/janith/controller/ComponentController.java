package com.janith.controller;

import com.janith.Service.IncludedComponentsService;
import com.janith.model.ComponentCategory;
import com.janith.model.IncludedComponents;
import com.janith.request.ComponentCategoryRequest;
import com.janith.request.ComponentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/components")
public class ComponentController {

    @Autowired
    private IncludedComponentsService includedComponentsService;

    @PostMapping("/category")
    public ResponseEntity<ComponentCategory> createComponentCategory(
            @RequestBody ComponentCategoryRequest req
    ) throws Exception {
        ComponentCategory item = includedComponentsService.createComponentCategory(req.getName(), req.getShopId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IncludedComponents> createComponent(
            @RequestBody ComponentRequest req) throws Exception {
        IncludedComponents item = includedComponentsService.createComponent(req.getShopId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IncludedComponents> updateComponentStock(
            @PathVariable Long id) throws Exception {
        IncludedComponents item = includedComponentsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<List<IncludedComponents>> getShopIncludedComponents(
            @PathVariable Long id) throws Exception {
        List<IncludedComponents> items = includedComponentsService.findShopIncludedComponents(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/shop/{id}/category")
    public ResponseEntity<List<ComponentCategory>> getShopComponentCategory(
            @PathVariable Long id) throws Exception {
        List<ComponentCategory> items = includedComponentsService.findComponentCategoryByShopId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
