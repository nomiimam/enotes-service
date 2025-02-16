package com.enote.enote.service;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();
    public CategoryDto getCategoryById(Integer id);
    public Boolean deleteCategory(Integer id);
}
