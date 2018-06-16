package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.entities.Comment;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.services.CommentService;
import com.adam.commoninterestsservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentRestController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Comment> readAllComments(@PathVariable Long postId) {
        return this.commentService.getAllByPostId(postId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody Comment input, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User author = userService.findByUsername(userDetails.getUsername());
        Comment created = this.commentService.addComment(input, postId, author);
        URI location = buildLocation(created);
        return ResponseEntity.created(location).build();
    }

    private URI buildLocation(Comment created) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{commentId}")
    ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        this.commentService.removeComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
