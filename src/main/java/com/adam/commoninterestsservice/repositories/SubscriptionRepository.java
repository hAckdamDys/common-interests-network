package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Subscription;
import com.adam.commoninterestsservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Collection<Subscription> findAllByUserId(Long userId);
    Optional<Subscription> findByCategoryName(String name);
    Optional<Subscription> findByCategoryAndUser(Category category, User user);
}
