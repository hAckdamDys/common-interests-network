package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.entities.Post;
import com.adam.commoninterestsservice.exceptions.PostNotFoundException;
import com.adam.commoninterestsservice.exceptions.PostReferenceViolationException;
import com.adam.commoninterestsservice.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Collection<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post addPost(String contents) {
        Post post = new Post(contents);
        return postRepository.save(post);
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
