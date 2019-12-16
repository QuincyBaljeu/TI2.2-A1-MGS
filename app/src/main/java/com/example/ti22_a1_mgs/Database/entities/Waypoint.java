package com.example.ti22_a1_mgs.Database.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "waypoint_table")
public class Waypoint {

    @PrimaryKey
    private int number;

    private double lat;
    private double lon;
    private boolean visited;
    @ForeignKey(entity = PointOfInterest.class, parentColumns = "pointOfInterestId", childColumns = "pointOfInterestId")
    private int pointOfInterestId;


    public Waypoint(int number, double lat, double lon, int pointOfInterestId) {
        this.number = number;
        this.lat = lat;
        this.lon = lon;
        this.pointOfInterestId = pointOfInterestId;
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

    public int getPointOfInterestId() {
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

    public void setPointOfInterestId(int pointOfInterestId) {
        this.pointOfInterestId = pointOfInterestId;
    }
}
