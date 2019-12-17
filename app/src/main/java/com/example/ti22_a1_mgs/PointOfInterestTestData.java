package com.example.ti22_a1_mgs;

import java.io.Serializable;

public class PointOfInterestTestData implements Serializable {

    public String title;
    public int randomNumber;

    public PointOfInterestTestData(String title, int randomNumber) {
        this.title = title;
        this.randomNumber = randomNumber;
    }

    public String getTitle() {
        return title;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
