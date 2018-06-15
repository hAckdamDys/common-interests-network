package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.dto.UserDTO;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.exceptions.UserNotFoundException;
import com.adam.commoninterestsservice.exceptions.UsernameTakenException;
import com.adam.commoninterestsservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserDTO input) {
        if (userRepository.findByUsername(input.getUsername()).isPresent()) {
            throw new UsernameTakenException(String.format("Username %s is already taken.", input.getUsername()));
        }
        userRepository.save(new User(input.getUsername(), passwordEncoder.encode(input.getPassword())));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("Username %s not found.", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        return user;
    }
}
