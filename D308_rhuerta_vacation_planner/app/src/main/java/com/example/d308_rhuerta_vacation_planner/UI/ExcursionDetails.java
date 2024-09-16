package com.example.d308_rhuerta_vacation_planner.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308_rhuerta_vacation_planner.R;
import com.example.d308_rhuerta_vacation_planner.database.Repository;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String excursionName;
    String date;
    int excursionId;
    int vacationId;
    EditText editExcursionName;
    EditText editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        excursionName = getIntent().getStringExtra("excursionName");
        editExcursionName = findViewById(R.id.excursion_title);
        excursionId = getIntent().getIntExtra("excursionId", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);
        editDate = findViewById(R.id.date);
        String myFormat = "mm/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Vacation> vacationArrayList= new ArrayList<>();
        vacationArrayList.addAll(repository.getmAllVacations());
        ArrayList<Integer> vacationIdList= new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationId());
        }
        ArrayAdapter<Integer> vacationIdAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner=findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editDate.getText().toString();
                if(info.equals(""))info="09/15/24";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabelStart() {
        String myFormat = "mm/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home){
            this.finish();
            return true;}

        if (item.getItemId()== R.id.save_excursion){
            Excursion excursion;;
            if (excursionId == -1) {
                if (repository.getmAllExcursions().size() == 0)
                    excursionId = 1;
                else
                    excursionId = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionId() + 1;
                excursion = new Excursion(excursionId, editExcursionName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionId, editExcursionName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.update(excursion);
            }
            return true;
        }
        return true;
    }
}
