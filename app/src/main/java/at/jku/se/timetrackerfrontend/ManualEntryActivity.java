package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Category;
import entities.Measurement;
import entities.Person;
import entities.Project;
import entities.TimeEntry;
import services.CategoryService;
import services.PersonService;
import services.ProjectService;
import services.TimeEntryService;

public class ManualEntryActivity extends AppCompatActivity {

    TimeEntryService timeEntryService;
    ProjectService projectService;
    CategoryService categoryService;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();

        List<Project> projectList = projectService.get();
        List<String> projectStringList = new ArrayList();
        projectStringList.add("");
        for(Project p : projectList){
            projectStringList.add(p.getId() + " " +p.getName());
        }
        Spinner spnProject = (Spinner) findViewById(R.id.spinner_manualEntry_projectSelection);
        ArrayAdapter<String> spnAdptProject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectStringList);
        spnProject.setAdapter(spnAdptProject);

        final Spinner spnCategory = (Spinner) findViewById(R.id.spinner_manualEntry_categorySelection);
        List<String> categoryStringList = new ArrayList();
        final ArrayAdapter<String> spnAdptCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryStringList);
        spnCategory.setAdapter(spnAdptCategory);
        spnProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                List<String> categoryStringList = new ArrayList();
                categoryStringList.add("");
                String selectedProjectName = parentView.getSelectedItem().toString();
                if(!selectedProjectName.equals("")) {
                    long selectedProjectId = Long.parseLong(selectedProjectName.substring(0, selectedProjectName.indexOf(' ')));
                    List<Category> categoryList = categoryService.get();
                    for (Category c : categoryList) {
                        if (c.getProject().getId() == selectedProjectId) {
                            categoryStringList.add(c.getId() + " " + c.getName());
                        }
                    }
                }
                spnAdptCategory.clear();
                spnAdptCategory.addAll(categoryStringList);
                spnAdptCategory.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){

            }
        });

        Button btnSaveEntry = (Button) findViewById(R.id.btn_manualEntry_save);
        btnSaveEntry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                EditText editTextFromDay = (EditText) findViewById(R.id.eText_manualEntry_fromDate);
                EditText editTextFromTime = (EditText) findViewById(R.id.eText_manualEntry_fromTime);
                EditText editTextToDay = (EditText) findViewById(R.id.eText_manualEntry_toDate);
                EditText editTextToTime = (EditText) findViewById(R.id.eText_manualEntry_toTime);
                String fromDate = editTextFromDay.getText().toString();
                String fromTime = editTextFromTime.getText().toString();
                String toDate = editTextToDay.getText().toString();
                String toTime = editTextToTime.getText().toString();
                Date from = null;
                Date to = null;
                try {
                    from = dateFormat.parse(fromDate + " " + fromTime);
                    to =  dateFormat.parse(toDate + " " + toTime);
                }catch(Exception ex){};
                EditText editTextNotice = (EditText) findViewById(R.id.eText_manualEntry_notice);
                String note = editTextNotice.getText().toString();
                Person person = LoginActivity.user;

                Category category = null;
                String categoryString = spnCategory.getSelectedItem().toString();
                if(!categoryString.equals("")) {
                    long categoryId = Long.parseLong(categoryString.substring(0, categoryString.indexOf(' ')));
                    category = categoryService.getById(categoryId);
                }
                timeEntryService.create(new TimeEntry(from, to, note, Measurement.MANUALLY, person, category));
                startActivity(new Intent(ManualEntryActivity.this, EditEntryActivity.class));
            }
        });

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
