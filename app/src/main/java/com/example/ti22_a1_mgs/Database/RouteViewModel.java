package com.example.ti22_a1_mgs.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.ti22_a1_mgs.Database.blindwalls.BlindWall;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.POIAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RouteViewModel extends AndroidViewModel {

    private static final String TAG = "RouteViewModel";

    private Reposetory repository;
    private LiveData<List<Waypoint>> allWayPoints;
    private LiveData<List<PointOfInterest>> allPointsOfInterest;
    private POIAdapter poiAdapter;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Reposetory(application);
        String json = JsonUtil.loadJSONFromAsset(this.getApplication().getApplicationContext());
        this.blindWallsBreda = BlindWallsBreda.createFromJson(json);
//        fillDatabaseFromData(blindWallsBreda.getAllWalls());      Moved to a activity
        this.allWayPoints = repository.getAllWaypoints();
        this.allPointsOfInterest = repository.getAllPointsOfInterest();
        this.poiAdapter = new POIAdapter();
    }

    public void deleteAllDatabaseContents() {
        Log.wtf(TAG, "########WARNING DELETING ALL DATABASE CONTENTS#########");
        this.repository.deleteAllPointsOfInterest();
        this.repository.deleteAllWaypoints();
    }

    public void fillDatabaseFromData(final List<BlindWall> blindWalls, LifecycleOwner observer) {
        for (final BlindWall wall : blindWalls) {
            repository.insert(new PointOfInterest(
                    wall.getWallId(),
                    wall.getDescriptionDutch(),
                    wall.getDescriptionEnglish(),
                    (ArrayList<String>) wall.getImagesUrls()));

            getPointOfInterestByLocationName(wall.getWallId()).observe(observer, new Observer<List<PointOfInterest>>() {
                @Override
                public void onChanged(List<PointOfInterest> pointOfInterests) {
                    for (PointOfInterest pointOfInterest : pointOfInterests) {
                        if (pointOfInterest.getId() == wall.getWallId()) {
                            repository.insert(new Waypoint(
                                    blindWalls.indexOf(wall),
                                    wall.getLatitude(),
                                    wall.getLongitude(),
                                    pointOfInterest.getId()));
                        }
                    }
                }
            });

        }
    }


    public void insert(Waypoint waypoint) {
        this.repository.insert(waypoint);
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
        this.repository.insert(pointOfInterest);
    }

    public void update(PointOfInterest pointOfInterest) {
        this.repository.update(pointOfInterest);
    }

    public void delete(PointOfInterest pointOfInterest) {
        this.repository.delete(pointOfInterest);
    }

    public void deleteAllPointsOfInterest() {
        this.repository.deleteAllPointsOfInterest();
    }

    public LiveData<List<PointOfInterest>> getPointOfInterestByLocationName(int locationId) {
        LiveData<List<PointOfInterest>> pointOfInterest = null;
        try {
            pointOfInterest = this.repository.getPointOfInterestByLocation(locationId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (pointOfInterest == null) {
            Log.wtf("RouteViewModel", "CouldntFind PointOfInterestByLocationName, reteurning null");
            pointOfInterest = new LiveData<List<PointOfInterest>>() {
            };
        }
        return pointOfInterest;
    }

    public POIAdapter getPoiAdapter() { return poiAdapter;}
    public void reloadList(List<PointOfInterest> points){

        poiAdapter.setPointOfInterests(points);
        System.out.println(points);
        poiAdapter.notifyDataSetChanged();
    }
    public void onAPICallback(List<PointOfInterest> blindwalls) {};

}
