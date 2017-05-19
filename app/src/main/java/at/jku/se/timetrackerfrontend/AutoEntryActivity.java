package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import entities.Category;
import entities.Cooperation;
import entities.Measurement;
import entities.Person;
import entities.Project;
import entities.TimeEntry;
import services.CategoryService;
import services.PersonService;
import services.ProjectService;
import services.TimeEntryService;

public class AutoEntryActivity extends AppCompatActivity{

    TimeEntryService timeEntryService;
    ProjectService projectService;
    CategoryService categoryService;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_entry);

        //initialise Services
        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();

        final Button btnStartTimer = (Button) findViewById(R.id.btn_autoEntry_start);
        final Button btnCancel = (Button) findViewById(R.id.btn_autoEntry_cancel);
        final Chronometer focus = (Chronometer) findViewById(R.id.chronometer);

        //initialise projectList and projectspinner
        List<Project> projectList = new LinkedList<Project>();
        List<Project> projectListAll = projectService.get();
        projectList.add(new Project("",""));

        for(Project p : projectListAll){
            List<Cooperation> cooperationList = p.getCooperations();
            for(Cooperation c : cooperationList){
                if(c.getPerson() == LoginActivity.user && c.getProject() == p){
                    projectList.add(p);
                }
            }
        }
        AppCompatSpinner spnProject = (AppCompatSpinner) findViewById(R.id.spinner_autoEntry_projectSelection);
        ArrayAdapter<Project> spnAdptProject = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_dropdown_item, projectList);
        spnProject.setAdapter(spnAdptProject);

        //initialise categoryList and categoryspinner
        final AppCompatSpinner spnCategory = (AppCompatSpinner) findViewById(R.id.spinner_autoEntry_categorySelection);

        List<Category> categoryList = new LinkedList<Category>();
        final ArrayAdapter<Category> spnAdptCategory = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        spnCategory.setAdapter(spnAdptCategory);
        spnProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                List<Category> categoryList = new LinkedList<Category>();
                categoryList.add(new Category("",0,null));
                Project selectedProject = (Project) parentView.getSelectedItem();
                if(!selectedProject.getName().equals("")) {
                    List<Category> categoryListAll = categoryService.get();
                    for(Category c : categoryListAll){
                        if(c.getProject() == selectedProject){
                            categoryList.add(c);
                        }
                    }
                }
                spnAdptCategory.clear();
                spnAdptCategory.addAll(categoryList);
                spnAdptCategory.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){

            }
        });

        //ButtonListener
        final Date[] from = new Date[1];
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStartTimer.getText().equals("Start")){
                    focus.setBase(SystemClock.elapsedRealtime());
                    focus.start();
                    btnStartTimer.setText("Save");
                    from[0] = new Date();
                }else{
                    focus.stop();
                    EditText editTextNotice = (EditText) findViewById(R.id.eText_autoEntry_notice);
                    String note = editTextNotice.getText().toString();
                    Person person = LoginActivity.user;

                    Category category = (Category) spnCategory.getSelectedItem();
                    if (category.getName().equals("")) {
                        category = null;
                    }

                    focus.getBase();
                    Date to = new Date();
                    timeEntryService.create(new TimeEntry(from[0], to, note, Measurement.AUTOMATICALLY, person, category));
                    startActivity(new Intent(AutoEntryActivity.this, EditEntryActivity.class));
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focus.stop();
                focus.setBase(SystemClock.elapsedRealtime());
                btnStartTimer.setText("Start");
                spnProject.setSelection(0);
                spnCategory.setSelection(0);
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