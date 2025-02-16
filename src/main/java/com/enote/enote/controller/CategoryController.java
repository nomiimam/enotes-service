package com.enote.enote.controller;

import com.enote.enote.entity.Category;
import com.enote.enote.service.CategoryService;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        Boolean result = categoryService.saveCategory(category);
        if (result) {
            return new ResponseEntity<>("Save success", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Save failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        List<Category> result = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
