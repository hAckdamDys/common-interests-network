package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.entities.Like;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.services.LikeService;
import com.adam.commoninterestsservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeRestController {

    private final LikeService likeService;
    private final UserService userService;

    @Autowired
    public LikeRestController(LikeService likeService, UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Like> readAllLikes(@PathVariable Long postId) {
        return this.likeService.getAllByPostId(postId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createLike(@PathVariable Long postId, Principal principal) {
        UserDetails userDetails = (UserDetails) principal;
        User user = userService.findByUsername(userDetails.getUsername());
        Like created = this.likeService.addLike(postId, user);
        URI location = buildLocation(created);
        return ResponseEntity.created(location).build();
    }

    private URI buildLocation(Like created) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{likeId}")
    ResponseEntity<?> deleteLike(@PathVariable Long likeId) {
        this.likeService.removeLike(likeId);
        return ResponseEntity.noContent().build();
    }
}
