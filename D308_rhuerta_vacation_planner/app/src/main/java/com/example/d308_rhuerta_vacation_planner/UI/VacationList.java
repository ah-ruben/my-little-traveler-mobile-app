package com.example.d308_rhuerta_vacation_planner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_rhuerta_vacation_planner.R;
import com.example.d308_rhuerta_vacation_planner.database.Repository;
import com.example.d308_rhuerta_vacation_planner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterVacations(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVacations(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_vacation) {
            repository=new Repository(getApplication());
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
            /*Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(1,"Italy","Hilton Hotel","04/12/25","05/12/25");
            repository.insert(vacation);
            vacation=new Vacation(2,"Spain","Hilton Hotel","06/10/25","07/08/25");
            repository.insert(vacation);
            Excursion excursion=new Excursion(1,"Boat Ride", "06/10/25", 1);
            repository.insert(excursion);
            excursion=new Excursion(2,"Horse Ride", "06/10/25", 2);
            repository.insert(excursion);*/
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    private void filterVacations(String query) {
        List<Vacation> allVacations = repository.getmAllVacations();
        List<Vacation> filteredVacations = new ArrayList<>();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        for (Vacation vacation : allVacations) {
            if (vacation.getVacationName().toLowerCase().contains(query.toLowerCase()) ||
                    vacation.getAccommodation().toLowerCase().contains(query.toLowerCase())) {
                filteredVacations.add(vacation);
            }
        }
        vacationAdapter.setVacations(filteredVacations);
    }
}
