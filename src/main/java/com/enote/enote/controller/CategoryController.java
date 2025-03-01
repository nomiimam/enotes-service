package com.enote.enote.controller;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;
import com.enote.enote.service.CategoryService;
import com.enote.enote.util.CommonUtil;
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
           return CommonUtil.createBuildResponseMessage("Save success", HttpStatus.CREATED);
        }else {
            return CommonUtil.createErrorResponseMessage("Save failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> result = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.noContent().build();
        }else {
            return CommonUtil.createBuildResponse(result, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> result = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.noContent().build();
        }else {
            return CommonUtil.createBuildResponse(result, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception {

        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(categoryDto)) {
            return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) {
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return CommonUtil.createBuildResponse("Category deleted success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Category Not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
