package com.example.ti22_a1_mgs.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "point_of_interest_table"
        , indices = {@Index("id")})
public class PointOfInterest {

    @PrimaryKey @NonNull
    private int id;
    private String addres;
    private String nlDescription;
    private String enDescription;
    private ArrayList<String> imgUrls;

    public PointOfInterest(int id, String addres, String nlDescription, String enDescription, ArrayList<String> imgUrls) {
        this.id = id;
        this.addres = addres;
        this.nlDescription = nlDescription;
        this.enDescription = enDescription;
        this.imgUrls = imgUrls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public void setNlDescription(String nlDescription) {
        this.nlDescription = nlDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "id='" + id + '\'' +
                ", nlDescription='" + nlDescription + '\'' +
                ", enDescription='" + enDescription + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
