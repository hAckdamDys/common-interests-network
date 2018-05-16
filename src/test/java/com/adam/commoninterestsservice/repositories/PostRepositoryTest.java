package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.CommonInterestsServiceApplication;
import com.adam.commoninterestsservice.entities.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonInterestsServiceApplication.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Collection<Post> posts;

    @Before
    public void setUp() {
        postRepository.deleteAllInBatch();
        posts = Stream.of("Hello everyone!", "Hi", "Welcome").map(Post::new)
                .peek(postRepository::save)
                .collect(Collectors.toList());
    }

    @Test
    public void findAll() {
        assertThat(postRepository.findAll().size(), is(posts.size()));
    }
}