package com.example.covid.model;

public class Statistics {
    private String ageGroup;
    private float percentage;

    public Statistics(String ageGroup, float percentage) {
        this.ageGroup = ageGroup;
        this.percentage = percentage;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
