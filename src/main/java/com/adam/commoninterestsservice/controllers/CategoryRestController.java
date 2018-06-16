package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Post;
import com.adam.commoninterestsservice.services.CategoryService;
import com.adam.commoninterestsservice.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final PostService postService;

    @Autowired
    public CategoryRestController(CategoryService categoryService, PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Category> readAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{categoryName}/posts")
    Collection<Post> getAllPostsByCategory(@PathVariable String categoryName) {
        return this.postService.getAllPostsByCategory(categoryName);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createCategory(@RequestBody Category input) {
        Category created = this.categoryService.addCategory(input);
        URI location = buildLocation(created);
        return ResponseEntity.created(location).build();
    }

    private URI buildLocation(Category created) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{categoryId}")
    Category readSingleCategory(@PathVariable Long categoryId) {
        return this.categoryService.get(categoryId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{categoryId}")
    ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category input) {
        this.categoryService.updateCategory(categoryId, input.getName());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{categoryId}")
    ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        this.categoryService.removeCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
