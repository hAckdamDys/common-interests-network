package com.adam.commoninterestsservice;

import com.adam.commoninterestsservice.services.CategoryService;
import com.adam.commoninterestsservice.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class CommonInterestsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonInterestsServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PostService postService, CategoryService categoryService) {
        return args -> {
            postService.addPost("Hello everyone");
            postService.addPost("Welcome");
            postService.addPost("Another post");
            categoryService.addCategory("programming");
            categoryService.addCategory("sport");
            categoryService.addCategory("football");
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/posts").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
