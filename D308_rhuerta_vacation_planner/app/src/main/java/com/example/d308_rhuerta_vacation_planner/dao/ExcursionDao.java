package com.example.d308_rhuerta_vacation_planner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308_rhuerta_vacation_planner.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursions ORDER BY excursionId ASC")
    List<Excursion> getAllExcursions();
    @Query("SELECT * FROM excursions WHERE vacationId=:vacId ORDER BY excursionId ASC")
    List<Excursion> getAssociatedExcursions(int vacId);
}
