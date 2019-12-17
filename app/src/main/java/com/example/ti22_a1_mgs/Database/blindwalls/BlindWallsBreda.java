package com.example.ti22_a1_mgs.Database.blindwalls;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul de Mast on 05-Nov-18.
 */

public class BlindWallsBreda {

    private static final String TAG = "BlindWallsBreda";

    private List<BlindWall> blindWalls = new ArrayList<>();

    public BlindWallsBreda() {
    }

    public List<BlindWall> getAllWalls() {
        return blindWalls;
    }

    @Override
    public String toString() {
        return "BlindWallsBreda{" +
                "blindWalls=" + blindWalls +
                '}';
    }

    public static BlindWallsBreda createFromJson(String json) {
        BlindWallsBreda blindWallsBreda = new BlindWallsBreda();

        JSONArray jsonWalls;
        JSONObject jsonWall;

        try {
            jsonWalls = new JSONArray(json);

            for (int i = 0; i < jsonWalls.length(); i++) {

                jsonWall = jsonWalls.getJSONObject(i);
                BlindWall wall = new BlindWall(jsonWall);

                blindWallsBreda.blindWalls.add(wall);

                Log.d(TAG, "createFromJson: " + wall);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return blindWallsBreda;
    }

    public String printAllWalls() {
        StringBuffer allWalls = new StringBuffer();
        for (BlindWall wall : blindWalls) {
            allWalls.append(wall.getArtist() + "\n");
        }
        return allWalls.toString();
    }
}
