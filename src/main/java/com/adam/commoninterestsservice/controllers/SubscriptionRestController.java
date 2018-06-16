package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Subscription;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.services.SubscriptionService;
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
@RequestMapping("/user/subscriptions")
public class SubscriptionRestController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @Autowired
    public SubscriptionRestController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Category> readAllSubscriptions(Authentication authentication) {
        Long userId = getUser(authentication).getId();
        return this.subscriptionService.getAllByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createSubscription(@RequestBody Category inputCategory, Authentication authentication) {
        Subscription created = this.subscriptionService.addSubscription(inputCategory, getUser(authentication));
        URI location = buildLocation(created);
        return ResponseEntity.created(location).build();
    }

    private User getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

    private URI buildLocation(Subscription created) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{subscriptionId}")
    ResponseEntity<?> deleteSubscription(@PathVariable Long subscriptionId) {
        this.subscriptionService.removeSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}

