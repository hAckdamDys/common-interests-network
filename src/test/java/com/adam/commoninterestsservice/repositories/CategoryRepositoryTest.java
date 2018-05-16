package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.CommonInterestsServiceApplication;
import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.exceptions.CategoryNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonInterestsServiceApplication.class)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Collection<Category> categories;

    @Before
    public void setUp() {
        categoryRepository.deleteAllInBatch();
        categories = Stream.of(new Category("sport"),
                new Category("movies"),
                new Category("football"))
                .peek(category -> categoryRepository.save(category))
                .collect(Collectors.toList());
    }

    @Test
    public void saveCategory() {
        String categoryName = "programming";
        Category category = new Category(categoryName);
        category = categoryRepository.save(category);
        assertFindCategoriesByName(categoryName);
        categoryRepository.delete(category);
    }

    @Test
    public void findCategoryByName() {
        assertFindCategoriesByName("sport");
    }

    @Test
    public void updateCategory() {
        String oldName = "sport";
        String newName = "soccer";
        Category category = getCategoryByName(oldName);
        category.setName(newName);
        categoryRepository.save(category);
        assertTrue(categoryRepository.findCategoriesByName(oldName).isEmpty());
        assertFindCategoriesByName(newName);
    }

    private void assertFindCategoriesByName(String name) {
        Collection<Category> found = categoryRepository.findCategoriesByName(name);
        assertFalse(found.isEmpty());
        assertTrue(found.stream()
                .map(Category::getName)
                .anyMatch(s -> s.equals(name)));
    }

    @Test
    public void deleteCategory() {
        String name = "sport";
        Category category = getCategoryByName(name);
        categoryRepository.delete(category);
        Collection<Category> found = categoryRepository.findCategoriesByName(name);
        assertTrue(found.isEmpty());
    }

    @Test
    public void findAll() {
        assertThat(categoryRepository.findAll().size(), is(categories.size()));
    }

    @Test
    public void findCategoryById() {
        String name = "football";
        Category category = getCategoryByName(name);
        assertTrue(categoryRepository.findById(category.getId()).isPresent());
    }

    private Category getCategoryByName(String name) {
        Collection<Category> found = categoryRepository.findCategoriesByName(name);
        return found.stream().findAny()
                .orElseThrow(() -> new CategoryNotFoundException(String.format("%s category not found", name)));
    }
}