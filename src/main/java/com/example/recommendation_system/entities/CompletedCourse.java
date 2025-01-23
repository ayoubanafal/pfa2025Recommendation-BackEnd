package com.example.recommendation_system.entities;

import jakarta.persistence.*;

@Entity
public class CompletedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String level;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CompletedCourse() {}

    public CompletedCourse(String title, String category, String level, User user) {
        this.title = title;
        this.category = category;
        this.level = level;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
