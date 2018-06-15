package com.adam.commoninterestsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Subscription extends JpaRepository<Subscription, Long> {
}
