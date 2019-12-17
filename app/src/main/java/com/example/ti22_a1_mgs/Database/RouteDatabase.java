package com.example.ti22_a1_mgs.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterestDao;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.Database.entities.WaypointDao;

@Database(entities = {Waypoint.class, PointOfInterest.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RouteDatabase extends RoomDatabase {

    private static RouteDatabase instance;

    public abstract WaypointDao waypointDao();
    public abstract PointOfInterestDao pointOfInterestDao();

    public static synchronized RouteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RouteDatabase.class, "test_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static  class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WaypointDao waypointDao;
        private PointOfInterestDao pointOfInterestDao;

        public PopulateDbAsyncTask(RouteDatabase database) {
            this.waypointDao = database.waypointDao();
            this.pointOfInterestDao = database.pointOfInterestDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // TODO: 12/9/2019 Insert standard data
            return null;
        }
    }


}
