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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_rhuerta_vacation_planner.R;
import com.example.d308_rhuerta_vacation_planner.database.Repository;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    String accommodation;
    String start;
    String end;
    EditText editName;
    EditText editAccommodation;
    TextView editStartDate;
    TextView editEndDate;
    int vacationId;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        editName = findViewById(R.id.trip_title);
        editAccommodation = findViewById(R.id.accommodation);
        editStartDate = findViewById(R.id.vacation_start_date_button);
        editEndDate = findViewById(R.id.vacation_end_date_button);
        vacationId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        accommodation = getIntent().getStringExtra("accommodation");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        editName.setText(name);
        editAccommodation.setText(accommodation);
        //editStartDate.setText(startDate);
        //editEndDate.setText(endDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        editStartDate.setText(start);
        editEndDate.setText(end);
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "09/15/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "09/15/24";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vid", vacationId);
                startActivity(intent);
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.excursion_recycler_view);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion excursion : repository.getmAllExcursions()) {
            if (excursion.getVacationId() == vacationId) {
                filteredExcursions.add(excursion);
            }
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if (item.getItemId() == R.id.save_vacation) {
            Vacation vacation;
            if (vacationId == -1) {
                if (repository.getmAllVacations().size() == 0) {
                    vacationId = 1;
                } else {
                    vacationId = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                    vacation = new Vacation(vacationId, editName.getText().toString(), editAccommodation.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());

                    String dateFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                    try {
                        Date start = sdf.parse(vacation.getStartDate());
                        Date end = sdf.parse(vacation.getEndDate());
                        if (end.before(start)) {
                            Toast.makeText(VacationDetails.this, "Uh Oh! Your vacation should start before it ends!", Toast.LENGTH_LONG).show();
                            return false;
                        } else {
                            Toast.makeText(VacationDetails.this, "Vacation saved!", Toast.LENGTH_LONG).show();
                            repository.insert(vacation);
                            this.finish();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    vacation = new Vacation(vacationId, editName.getText().toString(), editAccommodation.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    String dateFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                    try {
                        Date start = sdf.parse(vacation.getStartDate());
                        Date end = sdf.parse(vacation.getEndDate());
                        if (end.before(start)) {
                            Toast.makeText(VacationDetails.this, "Oops! Your vacation should start before it ends!", Toast.LENGTH_LONG).show();
                            return false;
                        } else {
                            Toast.makeText(VacationDetails.this, "Vacation updated!", Toast.LENGTH_LONG).show();
                            repository.insert(vacation);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        if(item.getItemId()== R.id.delete_vacation) {
            for (Vacation vacation : repository.getmAllVacations()) {
                if (vacation.getVacationId() == vacationId) currentVacation = vacation;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationId() == vacationId) ++numExcursions;
            }
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId()== R.id.save_excursion) {
            if (vacationId == -1)
                Toast.makeText(VacationDetails.this, "Please save vacation before adding excursions", Toast.LENGTH_LONG).show();
            else {
                int excursionId;
                if (repository.getmAllVacations().size() == 0) excursionId = 1;
                else
                    excursionId = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionId() + 1;
                RecyclerView recyclerView = findViewById(R.id.excursion_recycler_view);
                final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredExcursions = new ArrayList<>();
                for (Excursion e : repository.getmAllExcursions()) {
                    if (e.getVacationId() == vacationId) filteredExcursions.add(e);
                }
                excursionAdapter.setExcursions(filteredExcursions);
                return true;
            }
        }


        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.share_vacation) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Vacation Starting\n" + "Start Date: " + editStartDate.getText().toString() + " - End Date: " + editEndDate.getText().toString()
                                                    + "\nAccommodation: " + editAccommodation.getText().toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            Toast.makeText(this, "Vacation details are ready to share!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.vacation_notify) {
            String dateFromScreen = editStartDate.getText().toString();
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
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your vacation " + editName.getText().toString() + " is starting!");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dateFromScreen2 = editEndDate.getText().toString();
            String myFormat2 = "MM/dd/yy";
            SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate2 = null;
            try {
                myDate2 = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                long trigger = myDate2.getTime();
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your vacation " + editName.getText().toString() + " is ending!");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursion_recycler_view);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

}