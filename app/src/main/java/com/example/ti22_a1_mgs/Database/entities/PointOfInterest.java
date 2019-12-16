package com.example.ti22_a1_mgs.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "point_of_interest_table"
        , indices = {@Index("location")})
public class PointOfInterest {

    @PrimaryKey @NonNull
    private String location;
    private String nlDescription;
    private String enDescription;
    private ArrayList<String> imgUrls;

    public PointOfInterest(String location, String nlDescription, String enDescription, ArrayList<String> imgUrls) {
        this.location = location;
        this.nlDescription = nlDescription;
        this.enDescription = enDescription;
        this.imgUrls = imgUrls;
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

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "location='" + location + '\'' +
                ", nlDescription='" + nlDescription + '\'' +
                ", enDescription='" + enDescription + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
