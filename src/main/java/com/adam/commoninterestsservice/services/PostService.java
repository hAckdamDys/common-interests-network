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

import java.util.Collection;
import java.util.HashSet;
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

    public Post addPost(Post input, User author) {
        Post toSave = new Post();
        toSave.setAuthor(author);
        toSave.setContents(input.getContents());
        toSave.setCategories(input.getCategories() == null ? new HashSet<>() :
                input.getCategories().stream()
                        .map(category -> categoryRepository.findByName(category.getName())
                                .orElseGet(() -> categoryRepository.save(new Category(category.getName()))))
                        .collect(Collectors.toSet()));
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
