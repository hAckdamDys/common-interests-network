package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.entities.Post;
import com.adam.commoninterestsservice.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostRestController {

    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Post> getAllPosts() {
         return this.postService.getAllPosts();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createPost(@RequestBody Post input) {
        Post created = this.postService.addPost(input.getContents());
        URI location = buildPostLocation(created);
        return ResponseEntity.created(location).build();
    }

    private URI buildPostLocation(Post created) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{postId}")
    Post readSinglePost(@PathVariable Long postId) {
        return this.postService.get(postId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{postId}")
    ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post input) {
        this.postService.updatePost(postId, input.getContents());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{postId}")
    ResponseEntity<?> deletePost(@PathVariable Long postId) {
        this.postService.removePost(postId);
        return ResponseEntity.noContent().build();
    }
}
