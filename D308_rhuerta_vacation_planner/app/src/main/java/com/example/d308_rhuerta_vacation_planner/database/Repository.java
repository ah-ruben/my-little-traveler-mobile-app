package com.example.d308_rhuerta_vacation_planner.database;

import android.app.Application;

import com.example.d308_rhuerta_vacation_planner.dao.ExcursionDao;
import com.example.d308_rhuerta_vacation_planner.dao.VacationDao;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDao mExcursionDao;
    private VacationDao mVacationDao;
    private List<Vacation>mAllVacations;
    private List<Excursion>mAllExcursions;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor=Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        AppDatabaseBuilder db=AppDatabaseBuilder.getDatabase(application);
        mExcursionDao=db.excursionDao();
        mVacationDao=db.vacationDao();
    }

    public List<Vacation> getmAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDao.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllVacations;
    }

    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion> getmAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDao.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDao.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDao.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDao.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion> getAssociatedExcursions(int vacationId) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDao.getAssociatedExcursions(vacationId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }
}
