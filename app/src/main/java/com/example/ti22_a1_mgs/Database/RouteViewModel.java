package com.example.ti22_a1_mgs.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.ti22_a1_mgs.Database.blindwalls.BlindWall;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RouteViewModel extends AndroidViewModel {

    private Reposetory repository;
    private BlindWallsBreda blindWallsBreda;
    private LiveData<List<Waypoint>> allWayPoints;
    private LiveData<List<PointOfInterest>> allPointsOfInterest;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Reposetory(application);
        String json = JsonUtil.loadJSONFromAsset(this.getApplication().getApplicationContext());
        this.blindWallsBreda = BlindWallsBreda.createFromJson(json);
//        fillDatabaseFromData(blindWallsBreda.getAllWalls());      Moved to a activity
        this.allWayPoints = repository.getAllWaypoints();
        this.allPointsOfInterest = repository.getAllPointsOfInterest();
    }

    private void fillDatabaseFromData(final List<BlindWall> blindWalls, LifecycleOwner observer)
    {
        for (final BlindWall wall : blindWalls)
        {
            repository.inset(new PointOfInterest(
                    wall.getAddress(),
                    wall.getDescriptionDutch(),
                    wall.getDescriptionEnglish(),
                    (ArrayList<String>) wall.getImagesUrls()));
            try {
                repository.getPointOfInterestByLocation(wall.getAddress()).observe(observer, new Observer<List<PointOfInterest>>() {
                    @Override
                    public void onChanged(List<PointOfInterest> pointOfInterests) {
                        for (PointOfInterest pointOfInterest : pointOfInterests) {
                            if (pointOfInterest.getLocation().equals(wall.getAddress())) {
                                repository.inset(new Waypoint(
                                        blindWalls.indexOf(wall),
                                        wall.getLatitude(),
                                        wall.getLongitude(),
                                        pointOfInterest.getLocation()));
                            }
                        }
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public LiveData<List<PointOfInterest>> getPointOfInterestByLocationName(String locationName) {
        LiveData<List<PointOfInterest>> pointOfInterest = null;
        try {
            pointOfInterest = this.repository.getPointOfInterestByLocation(locationName);
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
}
