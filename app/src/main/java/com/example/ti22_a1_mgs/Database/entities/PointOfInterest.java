package com.example.ti22_a1_mgs.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "point_of_interest_table")
public class PointOfInterest {

    @PrimaryKey
    private String location;
    private String nlDescription;
    private String enDescription;

    public PointOfInterest(String location, String nlDescription, String enDescription) {
        this.location = location;
        this.nlDescription = nlDescription;
        this.enDescription = enDescription;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getNlDescription() {
        return nlDescription;
    }

    public String getEnDescription() {
        return enDescription;
    }
}
