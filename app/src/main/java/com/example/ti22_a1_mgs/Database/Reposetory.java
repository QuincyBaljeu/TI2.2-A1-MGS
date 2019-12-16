package com.example.ti22_a1_mgs.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterestDao;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.Database.entities.WaypointDao;

import java.util.List;


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

    public void inset(PointOfInterest pointOfInterest) {
        new InsertPointOfInterestAsyncTask(this.pointOfInterestDao).doInBackground(pointOfInterest);
    }

    public void update(PointOfInterest pointOfInterest) {
        new UpdatePointOfInterestAsyncTask(this.pointOfInterestDao).doInBackground(pointOfInterest);
    }

    public void delete(PointOfInterest pointOfInterest) {
        new DeletePointOfInterestAsyncTask(this.pointOfInterestDao).doInBackground(pointOfInterest);
    }

    public void deleteAllPointsOfInterest() {
        new DeleteAllPointsOfInterestAsyncTask(this.pointOfInterestDao).doInBackground();
    }

    private static class InsertPointOfInterestAsyncTask extends AsyncTask<PointOfInterest, Void, Void> {

        private PointOfInterestDao pointOfInterestDao;

        private InsertPointOfInterestAsyncTask(PointOfInterestDao noteDao) {
            this.pointOfInterestDao = noteDao;
        }

        @Override
        protected Void doInBackground(PointOfInterest... notes) {
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



//----------------------------------Waypionts----------------------------------\\
    public void inset(Waypoint waypoint) {
        new InsertWaypointAsyncTask(this.waypointDao).execute(waypoint);
    }

    public void update(Waypoint waypoint) {
        new UpdateWaypointAsyncTask(this.waypointDao).doInBackground(waypoint);
    }

    public void delete(Waypoint waypoint) {
        new DeleteWaypointAsyncTask(this.waypointDao).doInBackground(waypoint);
    }

    public void deleteAllWaypoints() {
        new DeleteWaypointAsyncTask(this.waypointDao).doInBackground();
    }

    private static class InsertWaypointAsyncTask extends AsyncTask<Waypoint, Void, Void> {

        private WaypointDao waypointDao;

        private InsertWaypointAsyncTask(WaypointDao noteDao) {
            this.waypointDao = noteDao;
        }

        @Override
        protected Void doInBackground(Waypoint... notes) {
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

}
