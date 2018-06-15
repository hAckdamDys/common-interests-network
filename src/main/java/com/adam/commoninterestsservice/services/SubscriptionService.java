package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.entities.Category;
import com.adam.commoninterestsservice.entities.Subscription;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.exceptions.CategoryNotFoundException;
import com.adam.commoninterestsservice.repositories.CategoryRepository;
import com.adam.commoninterestsservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, CategoryRepository categoryRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.categoryRepository = categoryRepository;
    }

    public void removeSubscription(Long subscriptionId) {
        this.subscriptionRepository.deleteById(subscriptionId);
    }

    public Subscription addSubscription(Category inputCategory, User user) {
        Subscription toSave = new Subscription();
        Category category = categoryRepository.findByName(inputCategory.getName()).orElseThrow(() -> new CategoryNotFoundException("Category " + inputCategory.getName() + " not found"));
        toSave.setCategory(category);
        toSave.setUser(user);
        return subscriptionRepository.save(toSave);
    }

    public Collection<Category> getAllByUserId(Long userId) {
        return subscriptionRepository.findAllByUserId(userId)
                .stream()
                .map(Subscription::getCategory)
                .collect(Collectors.toList());
    }
}
