package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Collection<Post> findPostsByCategoriesContains(Category category);
}
