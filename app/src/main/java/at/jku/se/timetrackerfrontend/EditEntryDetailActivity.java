package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import entities.TimeEntry;
import services.TimeEntryService;

public class EditEntryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_detail);


        /*
        pass the time entry id
        *************************************
         */
        Bundle bundle = getIntent().getExtras();
        long timeEntryId = bundle.getLong("timeEntry");

        TimeEntryService timeEntryService = new TimeEntryService();

        TimeEntry timeEntry = timeEntryService.getById(timeEntryId);
        /*
            *****************************
         */

        final Button btnOk = (Button) findViewById(R.id.btn_editEntry_ok);
        final Button btnCancel = (Button) findViewById(R.id.btn_editEntry_cancel);
        final Button btnDeleteEntry = (Button) findViewById(R.id.btn_editEntry_delete);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEntryDetailActivity.this, EditEntryActivity.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEntryDetailActivity.this, EditEntryActivity.class));
            }
        });

        btnDeleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEntryDetailActivity.this, EditEntryActivity.class));
            }
        });


        Spinner spnProject = (Spinner) findViewById(R.id.spinner_editEntry_projectSelection);
        String[] projects = new String[]{"PR SE", "PR SE Prototyp", "KT CE"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projects);
        spnProject.setAdapter(spinnerAdapter);

        Spinner spnCategory = (Spinner) findViewById(R.id.spinner_editEntry_categorySelection);
        String[] categories = new String[]{"Entwurf", "Prototyp", "Doku"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spnCategory.setAdapter(spinnerAdapter2);

        /*
         */
        DateFormat timeFormat = new SimpleDateFormat("H:mm");
        String timeFormatedFrom = timeFormat.format(timeEntry.getFrom());
        EditText time = (EditText) findViewById(R.id.textView23);
        time.setText(timeFormatedFrom);
        /*
        Example of using the object timeentry
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.autoEntry) {
            startActivity(new Intent(this, AutoEntryActivity.class));
        } else if (id == R.id.manEntry) {
            startActivity(new Intent(this, ManualEntryActivity.class));
        } else if (id == R.id.detailEntry) {
            startActivity(new Intent(this,EditEntryActivity.class));
        } else if (id == R.id.newProj) {
            startActivity(new Intent(this,CreateProjectActivity.class));
        } else if (id == R.id.manageProj) {
            startActivity(new Intent(this, ManageProjectActivity.class));
        } else if (id == R.id.projReport) {
            startActivity(new Intent(this, ProjectReportActivity.class));
        } else if (id == R.id.userReport) {
            startActivity(new Intent(this, UserReportActivity.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        return true;
    }
}
