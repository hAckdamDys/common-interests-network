package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Post;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.exceptions.PostNotFoundException;
import com.adam.commoninterestsservice.exceptions.PostReferenceViolationException;
import com.adam.commoninterestsservice.repositories.CategoryRepository;
import com.adam.commoninterestsservice.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public Collection<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Collection<Post> getAllPostsByCategory(String categoryName) {
        Optional<Category> categoryOpt = categoryRepository.findByName(categoryName);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            return postRepository.findPostsByCategoriesContains(category);
        }
        return new ArrayList<>();
    }

    public Post addPost(Post input, User author) {
        Post toSave = new Post();
        toSave.setAuthor(author);
        toSave.setContents(input.getContents() == null ? "(no-contents)" :
                (input.getContents().equals("") ? "(no-contents)" : input.getContents()));
        if (input.getCategories() == null) {
            toSave.setCategories(Collections.singleton(categoryRepository.findByName("no-category")
                    .orElseGet(() -> categoryRepository.save(new Category("no-category")))));
        } else {
            toSave.setCategories(input.getCategories().stream()
                    .map(category -> categoryRepository.findByName(category.getName())
                            .orElseGet(() -> categoryRepository.save(new Category(category.getName() == null ? "no-category" :
                                    (category.getName().equals("") ? "no-category" : category.getName())))))
                    .collect(Collectors.toSet()));
        }
        toSave.setComments(new HashSet<>());
        toSave.setLikes(new HashSet<>());
        return postRepository.save(toSave);
    }

    public void updatePost(Long postId, String contents) {
        Post post = get(postId);
        post.setContents(contents);
        postRepository.save(post);
    }

    public Post get(Long postId) {
        return this.postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %d not found", postId)));
    }

    public void removePost(Long postId) {
        try {
            postRepository.deleteById(postId);
        } catch (DataIntegrityViolationException e) {
            throw new PostReferenceViolationException(String.format("Category with id %d is referenced by other entities", postId));
        }
    }
}
