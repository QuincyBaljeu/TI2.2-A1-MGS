package com.example.ti22_a1_mgs.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterestDao;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.Database.entities.WaypointDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Reposetory {
    private PointOfInterestDao pointOfInterestDao;
    private WaypointDao waypointDao;

    private LiveData<List<Waypoint>> allWaypoints;
    private LiveData<List<PointOfInterest>> allPointsOfInterest;

    public Reposetory(Application application) {
        RouteDatabase database = RouteDatabase.getInstance(application);
        this.pointOfInterestDao = database.pointOfInterestDao();
        this.allPointsOfInterest = this.pointOfInterestDao.getAllPointsOfInterest();

        this.waypointDao= database.waypointDao();
        this.allWaypoints = this.waypointDao.getAllWaypoints();
    }

    public LiveData<List<Waypoint>> getAllWaypoints() {
        return allWaypoints;
    }

    public LiveData<List<PointOfInterest>> getAllPointsOfInterest() {
        return allPointsOfInterest;
    }

    public void insert(PointOfInterest pointOfInterest) {
        new InsertPointOfInterestAsyncTask(this.pointOfInterestDao).execute(pointOfInterest);
    }

    public void update(PointOfInterest pointOfInterest) {
        new UpdatePointOfInterestAsyncTask(this.pointOfInterestDao).execute(pointOfInterest);
    }

    public void delete(PointOfInterest pointOfInterest) {
        new DeletePointOfInterestAsyncTask(this.pointOfInterestDao).execute(pointOfInterest);
    }

    public void deleteAllPointsOfInterest() {
        new DeleteAllPointsOfInterestAsyncTask(this.pointOfInterestDao).execute();
    }

    public LiveData<List<PointOfInterest>> getPointOfInterestByLocation(int id) throws ExecutionException, InterruptedException {
        return new GetPointOfInterestById(this.pointOfInterestDao).execute(id).get();
    }



    private static class InsertPointOfInterestAsyncTask extends AsyncTask<PointOfInterest, Void, Void> {

        private static final String TAG = "InsertPointOfInterestAs";

        private PointOfInterestDao pointOfInterestDao;

        private InsertPointOfInterestAsyncTask(PointOfInterestDao noteDao) {
            this.pointOfInterestDao = noteDao;
        }

        @Override
        protected Void doInBackground(PointOfInterest... notes) {
//            Log.wtf(TAG, "inserting note: " + notes[0]);
            this.pointOfInterestDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdatePointOfInterestAsyncTask extends AsyncTask<PointOfInterest, Void, Void> {

        private PointOfInterestDao pointOfInterestDao;

        private UpdatePointOfInterestAsyncTask(PointOfInterestDao noteDao) {
            this.pointOfInterestDao = noteDao;
        }

        @Override
        protected Void doInBackground(PointOfInterest... notes) {
            this.pointOfInterestDao.update(notes[0]);
            return null;
        }
    }

    private static class DeletePointOfInterestAsyncTask extends AsyncTask<PointOfInterest, Void, Void> {

        private PointOfInterestDao pointOfInterestDao;

        private DeletePointOfInterestAsyncTask(PointOfInterestDao noteDao) {
            this.pointOfInterestDao = noteDao;
        }

        @Override
        protected Void doInBackground(PointOfInterest... notes) {
            this.pointOfInterestDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllPointsOfInterestAsyncTask extends AsyncTask<Void, Void, Void> {

        private PointOfInterestDao pointOfInterestDao;

        private DeleteAllPointsOfInterestAsyncTask(PointOfInterestDao noteDao) {
            this.pointOfInterestDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            this.pointOfInterestDao.deleteAllPointsOfInterest();
            return null;
        }
    }

    private static class GetPointOfInterestById extends AsyncTask<Integer, Void, LiveData<List<PointOfInterest>>>{
        private PointOfInterestDao pointOfInterestDao;

        public GetPointOfInterestById(PointOfInterestDao pointOfInterestDao) {
            this.pointOfInterestDao = pointOfInterestDao;
        }

        @Override
        protected LiveData<List<PointOfInterest>> doInBackground(Integer... integers) {
            return pointOfInterestDao.findPointOfInterestByName(integers[0]);
        }
    }



//----------------------------------Waypionts----------------------------------\\

    public Waypoint getWaypoint(String number) {
        // @number is the primary key in int
        //TODO: LUCAS VOEG DIT TOE AUB!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return null;
    }



    public void insert(Waypoint waypoint) {
        new InsertWaypointAsyncTask(this.waypointDao).execute(waypoint);
    }

    public void update(Waypoint waypoint) {
        new UpdateWaypointAsyncTask(this.waypointDao).execute(waypoint);
    }

    public void delete(Waypoint waypoint) {
        new DeleteWaypointAsyncTask(this.waypointDao).execute(waypoint);
    }

    public void deleteAllWaypoints() {
        new DeleteAllWaypointsAsyncTask(this.waypointDao).execute();
    }

    private static class InsertWaypointAsyncTask extends AsyncTask<Waypoint, Void, Void> {

        private WaypointDao waypointDao;

        private InsertWaypointAsyncTask(WaypointDao noteDao) {
            this.waypointDao = noteDao;
        }

        private static final String TAG = "InsertWaypointAsyncTask";

        @Override
        protected Void doInBackground(Waypoint... notes) {
//            Log.wtf(TAG, "inserting note: " + notes[0]);
            this.waypointDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateWaypointAsyncTask extends AsyncTask<Waypoint, Void, Void> {

        private WaypointDao waypointDao;

        private UpdateWaypointAsyncTask(WaypointDao noteDao) {
            this.waypointDao = noteDao;
        }

        @Override
        protected Void doInBackground(Waypoint... notes) {
            this.waypointDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteWaypointAsyncTask extends AsyncTask<Waypoint, Void, Void> {

        private WaypointDao waypointDao;

        private DeleteWaypointAsyncTask(WaypointDao noteDao) {
            this.waypointDao = noteDao;
        }

        @Override
        protected Void doInBackground(Waypoint... notes) {
            this.waypointDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllWaypointsAsyncTask extends AsyncTask<Void, Void, Void> {

        private WaypointDao waypointDao;

        private DeleteAllWaypointsAsyncTask(WaypointDao noteDao) {
            this.waypointDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.waypointDao.deleteAllWaypoints();
            return null;
        }
    }


    private static class BlindWallSubClass {
        private PointOfInterestDao pointOfInterestDao;
        private String adress;
        private String descriptionNL;
        private String descriptionEN;
        private double latitude;
        private double longitude;
        private ArrayList<String> urls;

        public BlindWallSubClass(PointOfInterestDao pointOfInterestDao, String adress, String descriptionNL, String descriptionEN, double latitude, double longitude, ArrayList<String> urls) {
            this.pointOfInterestDao = pointOfInterestDao;
            this.adress = adress;
            this.descriptionNL = descriptionNL;
            this.descriptionEN = descriptionEN;
            this.latitude = latitude;
            this.longitude = longitude;
            this.urls = urls;
        }
    }
}
