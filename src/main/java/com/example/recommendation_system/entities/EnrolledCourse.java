package com.example.recommendation_system.entities;

import jakarta.persistence.*;

@Entity
public class EnrolledCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String level;
    private double numericRatings;
    private int reviewCounts;
    private double similarity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public EnrolledCourse() {
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

    public double getNumericRatings() {
        return numericRatings;
    }

    public void setNumericRatings(double numericRatings) {
        this.numericRatings = numericRatings;
    }

    public int getReviewCounts() {
        return reviewCounts;
    }

    public void setReviewCounts(int reviewCounts) {
        this.reviewCounts = reviewCounts;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnrolledCourse(Long id, String title, String category, String level, double numericRatings, int reviewCounts, double similarity, User user) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.level = level;
        this.numericRatings = numericRatings;
        this.reviewCounts = reviewCounts;
        this.similarity = similarity;
        this.user = user;
    }
}
