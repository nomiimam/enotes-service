package com.enote.enote.service.serviceImpl;

import com.enote.enote.dto.CategoryDto;
import com.enote.enote.dto.CategoryResponse;
import com.enote.enote.entity.Category;
import com.enote.enote.exception.ResourceNotFoundException;
import com.enote.enote.repository.CategoryRepository;
import com.enote.enote.service.CategoryService;
import com.enote.enote.util.Validation;
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
    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        validation.categoryValidation(categoryDto);
        if(ObjectUtils.isEmpty(categoryDto.getId())){
            category.setIsDeleted(false);
           // category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }
        else {
            updateCategory(category);
        }
        Category saveCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(saveCategory);
    }

    private void updateCategory(Category category) {
        Optional<Category> findById = categoryRepository.findById(category.getId());
        if (findById.isPresent()) {
            Category existCategory = findById.get();
            category.setCreatedBy(existCategory.getCreatedBy());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setIsDeleted(existCategory.getIsDeleted());
//            category.setUpdatedBy(1);
//            category.setUpdatedOn(new Date());
        }
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
    public CategoryDto getCategoryById(Integer id) throws Exception {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
        return mapper.map(category, CategoryDto.class);
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
