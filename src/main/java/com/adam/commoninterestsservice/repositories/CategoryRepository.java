package com.adam.commoninterestsservice.repositories;

import com.adam.commoninterestsservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Collection<Category> findCategoriesByName(String name);
}
