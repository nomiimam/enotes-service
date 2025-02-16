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

import java.util.*;

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
        List<Category> categoryList = categoryRepository.findByIsDeletedFalse();
        return categoryList.stream().map(cat-> mapper.map(cat, CategoryDto.class)).toList();
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categoryList = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categoryList.stream().map(cat-> mapper.map(cat, CategoryResponse.class)).toList();
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findByIdAndIsDeletedFalse(id);
        return category.map(value -> mapper.map(value, CategoryDto.class)).orElse(null);
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            Category category1 = category.get();
            category1.setIsDeleted(true);
            categoryRepository.save(category1);
            return true;
        }
        return false;
    }
}
