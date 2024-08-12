package com.example.d308_rhuerta_vacation_planner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionId;
    private String excursionName;
    private String startDate;
    private String endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public Excursion(int excursionId, String excursionName, String startDate, String endDate, int vacationId) {
        this.excursionId = excursionId;
        this.excursionName = excursionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vacationId = vacationId;
    }
}
