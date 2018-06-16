package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.exceptions.CategoryNotFoundException;
import com.adam.commoninterestsservice.exceptions.CategoryReferenceViolationException;
import com.adam.commoninterestsservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category input) {
        Category toSave = new Category(input.getName() == null ? "no-category" :
                (input.getName().equals("") ? "no-category" : input.getName()));
        return categoryRepository.save(toSave);
    }

    public Category updateCategory(Long id, String name) {
        Category category = get(id);
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category get(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id %d not found", id)));
    }

    public void removeCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryReferenceViolationException(String.format("Category with id %d is referenced by other entities", id));
        }
    }
}
