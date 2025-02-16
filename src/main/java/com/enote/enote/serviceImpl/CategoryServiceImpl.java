package com.enote.enote.serviceImpl;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;
import com.enote.enote.entity.Category;
import com.enote.enote.repository.CategoryRepository;
import com.enote.enote.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());

        Category saveCategory = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(cat-> mapper.map(cat, CategoryDto.class)).toList();
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categoryList = categoryRepository.findByIsActiveTrue();
        return categoryList.stream().map(cat-> mapper.map(cat, CategoryResponse.class)).toList();
    }
}
