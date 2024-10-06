package com.example.d308_rhuerta_vacation_planner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308_rhuerta_vacation_planner.dao.ExcursionDao;
import com.example.d308_rhuerta_vacation_planner.dao.VacationDao;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 9, exportSchema = false)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();
    private static volatile AppDatabaseBuilder INSTANCE;

    static AppDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null){
            synchronized (AppDatabaseBuilder.class) {
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabaseBuilder.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
