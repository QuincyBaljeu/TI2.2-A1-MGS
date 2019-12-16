package com.example.ti22_a1_mgs.Database.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "waypoint_table", foreignKeys = {
        @ForeignKey(entity = PointOfInterest.class,
                parentColumns = "location",
                childColumns = "pointOfInterestId")
}, indices = {@Index("pointOfInterestId")})
public class Waypoint {

    @PrimaryKey
    private int number;

    private double lat;
    private double lon;
    private boolean visited;
    private String pointOfInterestId;


    public Waypoint(int number, double lat, double lon, @Nullable String pointOfInterestId) {
        this.number = number;
        this.lat = lat;
        this.lon = lon;
        if (pointOfInterestId != null) {
            this.pointOfInterestId = pointOfInterestId;
        }
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "number=" + number +
                ", lat=" + lat +
                ", lon=" + lon +
                ", visited=" + visited +
                ", pointOfInterestId=" + pointOfInterestId +
                '}';
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isVisited() {
        return visited;
    }

    public String getPointOfInterestId() {
        return pointOfInterestId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setPointOfInterestId(String pointOfInterestId) {
        this.pointOfInterestId = pointOfInterestId;
    }
}
