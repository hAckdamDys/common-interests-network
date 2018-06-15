package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findAllByPostId(Long postId);
}
