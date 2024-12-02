package com.example.d308_rhuerta_vacation_planner.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.d308_rhuerta_vacation_planner.dao.VacationDao;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.util.List;

public class VacationViewModel extends ViewModel {
    private final VacationDao vacationDao;
    public VacationViewModel(VacationDao vacationDao) {
        this.vacationDao = vacationDao;
    }
    public List<Vacation> searchVacations(String query) {
        return vacationDao.searchVacations("%" + query + "%");
    }
}
