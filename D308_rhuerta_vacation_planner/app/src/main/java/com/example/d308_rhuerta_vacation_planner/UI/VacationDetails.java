package com.example.d308_rhuerta_vacation_planner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class VacationDetails extends AppCompatActivity {
    String name;
    String accommodation;
    String startDate;
    String endDate;
    EditText editName;
    EditText editAccommodation;
    EditText editStartDate;
    EditText editEndDate;
    int vacationId;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        editName = findViewById(R.id.trip_title);
        editAccommodation = findViewById(R.id.accommodation);
        editStartDate = findViewById(R.id.start_date);
        editEndDate = findViewById(R.id.end_date);
        vacationId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        accommodation = getIntent().getStringExtra("accommodation");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editName.setText(name);
        editAccommodation.setText(accommodation);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
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
                if (repository.getmAllVacations().size() == 0) vacationId = 1;
                else
                    vacationId = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                vacation = new Vacation(vacationId, editName.getText().toString(), editAccommodation.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                try {
                    vacation = new Vacation(vacationId, editName.getText().toString(), editAccommodation.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(vacation);
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
        if(item.getItemId()== R.id.add_excursion){
            if (vacationId == -1)
                Toast.makeText(VacationDetails.this, "Please save vacation before adding excursions", Toast.LENGTH_LONG).show();

            else {
                int excursionId;

                if (repository.getmAllVacations().size() == 0) excursionId = 1;
                else
                    excursionId = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionId() + 1;
                Excursion excursion = new Excursion(excursionId, "Beach Trip", "00/00/0000", vacationId);
                repository.insert(excursion);
                excursion = new Excursion(++excursionId, "Hike", "00/00/0000", vacationId);
                repository.insert(excursion);
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
        return super.onOptionsItemSelected(item);
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