package com.example.recommendation_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String level; // Beginner, Intermediate, Advanced
    private double numericRatings;
    private int reviewCounts;
    private double similarity;

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
}
