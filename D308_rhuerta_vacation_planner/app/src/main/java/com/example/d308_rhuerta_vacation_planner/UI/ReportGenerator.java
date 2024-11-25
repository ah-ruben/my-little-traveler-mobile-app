package com.example.d308_rhuerta_vacation_planner.UI;

import android.content.Context;
import android.os.Environment;

import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    public static String generateReport(Context context, List<Vacation> vacations, List<Excursion> excursions) {
        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + "/VacationAndExcursionReport_" + System.currentTimeMillis() + ".csv";
        try(FileWriter writer = new FileWriter(filePath)) {
            writer.append("Vacation Name,Accommodation,Start Date,End Date\n");
            for (Vacation vacation : vacations) {
                writer.append(vacation.getVacationName()).append(",")
                        .append(vacation.getAccommodation()).append(",")
                        .append(vacation.getStartDate()).append(",")
                        .append(vacation.getEndDate()).append("\n");
            }

            writer.append("\nExcursion Name,Date,Associated Vacation\n");
            for (Excursion excursion : excursions) {
                String associatedVacationName = "";
                for (Vacation vacation : vacations) {
                    if (vacation.getVacationId() == excursion.getVacationId()) {
                        associatedVacationName = vacation.getVacationName();
                        break;
                    }
                }
                writer.append(excursion.getExcursionName()).append(",")
                        .append(excursion.getDate()).append(",")
                        .append(associatedVacationName).append("\n");
            }
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
