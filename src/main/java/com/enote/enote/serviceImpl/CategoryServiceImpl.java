package com.enote.enote.serviceImpl;

import com.enote.enote.entity.Category;
import com.enote.enote.repository.CategoryRepository;
import com.enote.enote.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean saveCategory(Category category) {
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
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
