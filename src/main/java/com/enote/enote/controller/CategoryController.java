package com.enote.enote.controller;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;
import com.enote.enote.exception.ResourceNotFoundException;
import com.enote.enote.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        Boolean result = categoryService.saveCategory(categoryDto);
        if (result) {
            return new ResponseEntity<>("Save success", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Save failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> result = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> result = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        try {
            CategoryDto result = categoryService.getCategoryById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) {
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return new ResponseEntity<>("Category deleted successfully with id: "+id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
