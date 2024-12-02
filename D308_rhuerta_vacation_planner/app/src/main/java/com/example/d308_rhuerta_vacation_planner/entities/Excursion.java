package com.example.d308_rhuerta_vacation_planner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionId;
    private String excursionName;
    private String date;
    private int vacationId;

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public Excursion(int excursionId, String excursionName, String date, int vacationId) {
        this.excursionId = excursionId;
        this.excursionName = excursionName;
        this.date = date;
        this.vacationId = vacationId;
    }
}
