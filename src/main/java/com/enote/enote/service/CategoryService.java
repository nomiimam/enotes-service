package com.enote.enote.service;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;
import com.enote.enote.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();

}
