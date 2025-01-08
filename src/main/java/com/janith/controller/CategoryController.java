package com.janith.controller;

import com.janith.Service.CategoryService;
import com.janith.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorized")){

    }
}
