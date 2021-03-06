package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Collection<Like> findAllByPostId(Long postId);
}
