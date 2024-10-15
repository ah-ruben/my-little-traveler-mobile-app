package com.example.d308_rhuerta_vacation_planner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308_rhuerta_vacation_planner.R;
import com.example.d308_rhuerta_vacation_planner.database.Repository;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String excursionName;
    String date;
    int excursionId;
    int vacationId;
    Excursion currentExcursion;
    EditText editExcursionName;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        editExcursionName = findViewById(R.id.excursion_title);
        editDate = findViewById(R.id.excursion_date_button);
        excursionName = getIntent().getStringExtra("name");
        excursionId = getIntent().getIntExtra("eid", -1);
        vacationId = getIntent().getIntExtra("vid", -1);
        date = getIntent().getStringExtra("date");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        editExcursionName.setText(excursionName);
        editDate.setText(date);

        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getmAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationId());
        }
        /*ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);*/

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
                String info = editDate.getText().toString();
                if (info.equals("")) info = "01/01/25";
                try {
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
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.delete_excursion) {
            List<Excursion> allExcursions = new ArrayList<>(repository.getmAllExcursions());
            boolean flag=false;
            for (Excursion excursion : allExcursions) {
                if (excursion.getExcursionId() == excursionId) {
                    currentExcursion = excursion;
                    repository.delete(currentExcursion);
                    Toast.makeText(this, "Excursion was deleted", Toast.LENGTH_SHORT).show();
                    flag = true;
                    break;
                }
            }
            if (!flag)
                Toast.makeText(this, "Excursion was not deleted", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == R.id.save_excursion) {
            Excursion excursion;
            Vacation vacation = null;

            if (vacationId == -1) {
                Toast.makeText(ExcursionDetails.this, "Please save vacation before adding excursions", Toast.LENGTH_LONG).show();
                return false;
            }
            List<Vacation> allVacations = repository.getmAllVacations();
            for (Vacation vac : allVacations) {
                if (vac.getVacationId() == vacationId) {
                    vacation = vac;
                    break;
                }
            }

            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = sdf.parse(vacation.getStartDate());
                endDate = sdf.parse(vacation.getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            Date excursionDate = null;
            try {
                excursionDate = sdf.parse(editDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

            if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                Toast.makeText(ExcursionDetails.this, "Oops! Your excursion must be during your vacation!", Toast.LENGTH_LONG).show();
                return false;
            }

            if (excursionId == -1) {
                if (repository.getmAllExcursions().size() == 0) {
                    excursionId = 1;
                } else {
                    excursionId = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionId() + 1;
                }

                excursion = new Excursion(excursionId, editExcursionName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.insert(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion created!", Toast.LENGTH_LONG).show();
            }
            else {
                excursion = new Excursion(excursionId, editExcursionName.getText().toString(), editDate.getText().toString(), vacationId);
                repository.update(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion updated!", Toast.LENGTH_LONG).show();
            }
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.share_excursion) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, editExcursionName.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Date: " + editDate.getText().toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            Toast.makeText(this, "Excursion details are ready to share!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.excursion_notify) {
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your excursion " + editExcursionName.getText().toString() + " is coming up!");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
