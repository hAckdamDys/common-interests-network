package com.adam.commoninterestsservice.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contents;

    @ManyToMany(mappedBy = "posts")
    private Set<Category> categories;

    public Post(String contents) {
        this.contents = contents;
    }

    protected Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
