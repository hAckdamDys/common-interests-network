package com.adam.commoninterestsservice.controllers;

import com.adam.commoninterestsservice.dto.UserDTO;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public UserDTO getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = this.userService.findByUsername(userDetails.getUsername());
        UserDTO response = new UserDTO(user.getUsername());
        response.setId(user.getId());
        return response;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDTO input) {
        this.userService.addUser(input);
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/me").build().toUri();
        return ResponseEntity.created(location).build();
    }
}
