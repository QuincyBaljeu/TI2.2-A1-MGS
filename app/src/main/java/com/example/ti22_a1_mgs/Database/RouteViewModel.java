package com.example.ti22_a1_mgs.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ti22_a1_mgs.Database.blindwalls.BlindWall;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.POIAdapter;

import java.util.List;

public class RouteViewModel extends AndroidViewModel {

    private Reposetory repository;
    private LiveData<List<Waypoint>> allWayPoints;
    private LiveData<List<PointOfInterest>> allPointsOfInterest;
    private POIAdapter poiAdapter;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Reposetory(application);
        this.allWayPoints = repository.getAllWaypoints();
        this.allPointsOfInterest = repository.getAllPointsOfInterest();
        this.poiAdapter = new POIAdapter();
    }



    public void insert(Waypoint waypoint) {
        this.repository.inset(waypoint);
    }

    public void update(Waypoint waypoint) {
        this.repository.update(waypoint);
    }

    public void delete(Waypoint waypoint) {
        this.repository.delete(waypoint);
    }

    public void deleteAllNotes() {
        this.repository.deleteAllWaypoints();
    }

    public LiveData<List<Waypoint>> getAllWayPoints() {
        return allWayPoints;
    }

    public LiveData<List<PointOfInterest>> getAllPointsOfInterest() {
        return allPointsOfInterest;
    }

    public void insert(PointOfInterest pointOfInterest) {
        this.repository.inset(pointOfInterest);
    }

    public void update(PointOfInterest pointOfInterest) {
        this.repository.update(pointOfInterest);
    }

    public void delete(PointOfInterest pointOfInterest) {
        this.repository.delete(pointOfInterest);
    }

    public void deleteAllPointsOfInterest() {this. repository.deleteAllPointsOfInterest();}

    public void onAPICallback(List<PointOfInterest> blindwalls) {};

    public POIAdapter getPoiAdapter() { return poiAdapter;}

    public void reloadList(List<PointOfInterest> points){
        poiAdapter.setPointOfInterests(points);
        System.out.println(points);
        poiAdapter.notifyDataSetChanged();
    }
}
