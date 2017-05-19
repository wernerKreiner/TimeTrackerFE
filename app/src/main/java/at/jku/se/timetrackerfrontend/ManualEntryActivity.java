package at.jku.se.timetrackerfrontend;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class ManualEntryActivity extends AppCompatActivity {

    TimeEntryService timeEntryService;
    ProjectService projectService;
    CategoryService categoryService;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        //initialise Services
        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();


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

        AppCompatSpinner spnProject = (AppCompatSpinner) findViewById(R.id.spinner_manualEntry_projectSelection);
        ArrayAdapter<Project> spnAdptProject = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_dropdown_item, projectList);
        spnProject.setAdapter(spnAdptProject);

        //initialise categoryList and categoryspinner
        final AppCompatSpinner spnCategory = (AppCompatSpinner) findViewById(R.id.spinner_manualEntry_categorySelection);

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
        Button btnSaveEntry = (Button) findViewById(R.id.btn_manualEntry_save);
        btnSaveEntry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(allDatesFilled()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    EditText eTextFromDay = (EditText) findViewById(R.id.eText_manualEntry_fromDate);
                    EditText eTextFromTime = (EditText) findViewById(R.id.eText_manualEntry_fromTime);
                    EditText eTextToDay = (EditText) findViewById(R.id.eText_manualEntry_toDate);
                    EditText eTextToTime = (EditText) findViewById(R.id.eText_manualEntry_toTime);
                    String fromDate = eTextFromDay.getText().toString();
                    String fromTime = eTextFromTime.getText().toString();
                    String toDate = eTextToDay.getText().toString();
                    String toTime = eTextToTime.getText().toString();

                    Date from = null;
                    Date to = null;
                    try {
                        from = dateFormat.parse(fromDate + " " + fromTime);
                        to = dateFormat.parse(toDate + " " + toTime);
                    } catch (Exception ex) {
                    };

                    if (from.getTime() <= to.getTime()) {
                        EditText editTextNotice = (EditText) findViewById(R.id.eText_manualEntry_notice);
                        String note = editTextNotice.getText().toString();
                        Person person = LoginActivity.user;
                        Category category = (Category) spnCategory.getSelectedItem();
                        if (category.getName().equals("")) {
                            category = null;
                        }
                        timeEntryService.create(new TimeEntry(from, to, note, Measurement.MANUALLY, person, category));
                        startActivity(new Intent(ManualEntryActivity.this, EditEntryActivity.class));
                    } else {
                        Toast toast = new Toast(ManualEntryActivity.this);
                        toast.makeText(ManualEntryActivity.this, "Endtime is earlier than starttime!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast toast = new Toast(ManualEntryActivity.this);
                    toast.makeText(ManualEntryActivity.this, "Fill out all dates and times", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Timepicker and Datepicker
        final EditText fromTime = (EditText) findViewById(R.id.eText_manualEntry_fromTime);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManualEntryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final EditText toTime = (EditText) findViewById(R.id.eText_manualEntry_toTime);
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManualEntryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        toTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        final EditText fromDate = (EditText) findViewById(R.id.eText_manualEntry_fromDate);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ManualEntryActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay){
                        fromDate.setText(String.format("%02d.%02d.%04d",selectedDay,selectedMonth+1,selectedYear));
                        setDuration();
                    }
                }, year, month, day);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        final EditText toDate = (EditText) findViewById(R.id.eText_manualEntry_toDate);
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ManualEntryActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay){
                        toDate.setText(String.format("%02d.%02d.%04d",selectedDay,selectedMonth+1,selectedYear));
                        setDuration();
                    }
                }, year, month, day);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }

    public boolean allDatesFilled(){
        EditText eTextFromDay = (EditText) findViewById(R.id.eText_manualEntry_fromDate);
        EditText eTextFromTime = (EditText) findViewById(R.id.eText_manualEntry_fromTime);
        EditText eTextToDay = (EditText) findViewById(R.id.eText_manualEntry_toDate);
        EditText eTextToTime = (EditText) findViewById(R.id.eText_manualEntry_toTime);
        if(!eTextFromDay.getText().toString().equals("") && !eTextFromTime.getText().toString().equals("") &&
                !eTextToDay.getText().toString().equals("") && !eTextToTime.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public void setDuration(){
        if(allDatesFilled()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            EditText eTextFromDay = (EditText) findViewById(R.id.eText_manualEntry_fromDate);
            EditText eTextFromTime = (EditText) findViewById(R.id.eText_manualEntry_fromTime);
            EditText eTextToDay = (EditText) findViewById(R.id.eText_manualEntry_toDate);
            EditText eTextToTime = (EditText) findViewById(R.id.eText_manualEntry_toTime);
            String fromDate = eTextFromDay.getText().toString();
            String fromTime = eTextFromTime.getText().toString();
            String toDate = eTextToDay.getText().toString();
            String toTime = eTextToTime.getText().toString();
            Date newFrom = null;
            Date newTo = null;
            try {
                newFrom = dateFormat.parse(fromDate + " " + fromTime);
                newTo =  dateFormat.parse(toDate + " " + toTime);
            }catch(Exception ex){};
            long diff = newTo.getTime() - newFrom.getTime();
            diff = diff/1000;
            long hours = diff/3600;
            long min = (diff%3600)/60;
            TextView tViewDuration = (TextView) findViewById(R.id.tView_manualEntry_duration);
            tViewDuration.setText(String.format("%02d:%02d", hours, min));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
