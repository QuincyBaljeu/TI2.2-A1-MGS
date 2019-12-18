package com.example.ti22_a1_mgs.Database.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WaypointDao {

    @Insert
    void insert(Waypoint waypoint);

    @Update
    void update(Waypoint waypoint);

    @Delete
    void delete(Waypoint waypoint);


    @Query("DELETE FROM waypoint_table")
    void deleteAllWaypoints();

    @Query("SELECT * FROM waypoint_table ORDER BY number ASC")
    LiveData<List<Waypoint>> getAllWaypoints();

    @Query("SELECT * FROM waypoint_table WHERE number=:waypointNumber")
    LiveData<List<Waypoint>> findPointOfInterestByName(final Integer waypointNumber);

}
