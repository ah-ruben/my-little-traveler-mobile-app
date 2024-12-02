package com.example.d308_rhuerta_vacation_planner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacations ORDER BY vacationId ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM vacations WHERE vacationName LIKE :searchQuery OR accommodation LIKE :searchQuery ORDER BY vacationId ASC")
    List<Vacation> searchVacations(String searchQuery);
}
