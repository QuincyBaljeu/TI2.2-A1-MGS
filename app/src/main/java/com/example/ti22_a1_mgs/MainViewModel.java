package com.example.ti22_a1_mgs;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ti22_a1_mgs.Database.Reposetory;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWall;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;


import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String tag = "MainViewModel";

    private Reposetory repository;
    private BlindWallsBreda blindWallsBreda;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Reposetory(application);
        String json = JsonUtil.loadJSONFromAsset(application.getApplicationContext());
        this.blindWallsBreda = BlindWallsBreda.createFromJson(json);

        //Todo: Conditions for clearing and refilling database
        if(true) {
            clearDataBaseForNewData();
            fillDatabaseFromData(blindWallsBreda.getAllWalls());
        }
    }

    private void clearDataBaseForNewData()
    {
        Log.d(tag, "CLEARING WAYPOINTS AND POINTS OF INTEREST");
        this.repository.deleteAllWaypoints();
        this.repository.deleteAllPointsOfInterest();
    }

    private void fillDatabaseFromData(List<BlindWall> blindWalls)
    {
        for (BlindWall wall : blindWalls)
        {
            Log.d(tag, "Writing Blindwall: " +wall.toString());
            repository.addBlindWall(
                    wall.getAddress(),
                    wall.getDescriptionDutch(),
                    wall.getDescriptionEnglish(),
                    wall.getLatitude(),
                    wall.getLongitude());
        }
    }
}
