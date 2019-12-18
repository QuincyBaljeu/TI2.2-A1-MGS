package com.example.ti22_a1_mgs.Database.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PointOfInterestDao {

    @Insert
    void insert(PointOfInterest pointOfInterest);

    @Update
    void update(PointOfInterest pointOfInterest);

    @Delete
    void delete(PointOfInterest pointOfInterest);


    @Query("DELETE FROM point_of_interest_table")
    void deleteAllPointsOfInterest();

    @Query("SELECT * FROM point_of_interest_table ORDER BY id ASC")
    LiveData<List<PointOfInterest>> getAllPointsOfInterest();

    @Query("SELECT * FROM point_of_interest_table WHERE id=:locationId")
    LiveData<List<PointOfInterest>> findPointOfInterestByName(final Integer locationId);
}
