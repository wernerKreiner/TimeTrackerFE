package at.jku.se.timetrackerfrontend;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Category;
import entities.Project;
import entities.TimeEntry;
import services.CategoryService;
import services.PersonService;
import services.ProjectService;
import services.TimeEntryService;

public class EditEntryDetailActivity extends AppCompatActivity {

    TimeEntryService timeEntryService;
    ProjectService projectService;
    CategoryService categoryService;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_detail);

        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();

        final boolean firstCall = true;

        Bundle bundle = getIntent().getExtras();
        long timeEntryId = bundle.getLong("timeEntry");
        TimeEntry actualTimeEntry = timeEntryService.getById(timeEntryId);

        final Button btnOk = (Button) findViewById(R.id.btn_editEntry_ok);
        final Button btnCancel = (Button) findViewById(R.id.btn_editEntry_cancel);
        final Button btnDeleteEntry = (Button) findViewById(R.id.btn_editEntry_delete);


        EditText notice = (EditText) findViewById(R.id.eText_editEntryDetail_notice);
        notice.setText(actualTimeEntry.getNote());

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        EditText fromDate = (EditText) findViewById(R.id.eText_editEntryDetail_fromDate);
        fromDate.setText(dateFormat.format(actualTimeEntry.getFrom()));
        EditText fromTime = (EditText) findViewById(R.id.eText_editEntryDetail_fromTime);
        fromTime.setText(timeFormat.format(actualTimeEntry.getFrom()));
        EditText toDate = (EditText) findViewById(R.id.eText_editEntryDetail_toDate);
        toDate.setText(dateFormat.format(actualTimeEntry.getTo()));
        EditText toTime = (EditText) findViewById(R.id.eText_editEntryDetail_toTime);
        toTime.setText(timeFormat.format(actualTimeEntry.getTo()));

        setDuration(actualTimeEntry.getTo().getTime()- actualTimeEntry.getFrom().getTime());

        Category actualCategory;
        Project actualProject;

        if(actualTimeEntry.getCategory() != null) {
            actualCategory = actualTimeEntry.getCategory();
            actualProject = actualCategory.getProject();
        }else{
            actualCategory = null;
            actualProject = null;
        }

        List<Project> projectList = projectService.get();
        List<String> projectStringList = new ArrayList();
        projectStringList.add("");
        int spnProjectIndex = 0;
        int i = 0;
        for(Project p : projectList){
            projectStringList.add(p.getId() + " " +p.getName());
            i++;
            if(actualCategory != null && p.getId() == actualProject.getId()){
                spnProjectIndex = i;
            }
        }

        Spinner spnProject = (Spinner) findViewById(R.id.spinner_editEntry_projectSelection);
        ArrayAdapter<String> spnAdptProject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectStringList);
        spnProject.setAdapter(spnAdptProject);
        spnProject.setSelection(spnProjectIndex);

        List<String> categoryStringList = new ArrayList();
        categoryStringList.add("");
        Spinner spnCategory = (Spinner) findViewById(R.id.spinner_editEntry_categorySelection);
        ArrayAdapter<String> spnAdptCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryStringList);
        spnCategory.setAdapter(spnAdptCategory);

        spnProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                int spnCategoryIndex;
                List<Category> categoryList = categoryService.get();
                List<String> categoryStringList = new ArrayList();
                String selectedProjectName = parentView.getSelectedItem().toString();
                categoryStringList.add("");
                spnCategoryIndex = 0;
                int i = 0;
                if(!selectedProjectName.equals("")) {
                    long selectedProjectId = Long.parseLong(selectedProjectName.substring(0, selectedProjectName.indexOf(' ')));
                    if(actualProject != null && actualProject.getId() == selectedProjectId) {
                        for (Category c : categoryList) {
                            if (c.getProject().getId() == actualProject.getId()) {
                                categoryStringList.add(c.getId() + " " + c.getName());
                                i++;
                                if (c.getId() == actualCategory.getId()) {
                                    spnCategoryIndex = i;
                                }
                            }
                        }
                    }else{
                        for (Category c : categoryList){
                            if (c.getProject().getId() == selectedProjectId) {
                                categoryStringList.add(c.getId() + " " + c.getName());
                            }
                        }
                    }
                }
                spnAdptCategory.clear();
                spnAdptCategory.addAll(categoryStringList);
                spnAdptCategory.notifyDataSetChanged();
                spnCategory.setSelection(spnCategoryIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                EditText editTextFromDay = (EditText) findViewById(R.id.eText_editEntryDetail_fromDate);
                EditText editTextFromTime = (EditText) findViewById(R.id.eText_editEntryDetail_fromTime);
                EditText editTextToDay = (EditText) findViewById(R.id.eText_editEntryDetail_toDate);
                EditText editTextToTime = (EditText) findViewById(R.id.eText_editEntryDetail_toTime);
                String fromDate = editTextFromDay.getText().toString();
                String fromTime = editTextFromTime.getText().toString();
                String toDate = editTextToDay.getText().toString();
                String toTime = editTextToTime.getText().toString();
                Date newFrom = null;
                Date newTo = null;
                try {
                    newFrom = dateFormat.parse(fromDate + " " + fromTime);
                    newTo =  dateFormat.parse(toDate + " " + toTime);
                }catch(Exception ex){};


                if(newFrom.getTime() <= newTo.getTime()){
                    actualTimeEntry.setFrom(newFrom);
                    actualTimeEntry.setTo(newTo);

                    Category newCategory = null;
                    String categoryString = spnCategory.getSelectedItem().toString();
                    if(!categoryString.equals("")) {
                        long categoryId = Long.parseLong(categoryString.substring(0, categoryString.indexOf(' ')));
                        newCategory = categoryService.getById(categoryId);
                    }
                    actualTimeEntry.setCategory(newCategory);

                    actualTimeEntry.setNote(notice.getText().toString());

                    startActivity(new Intent(EditEntryDetailActivity.this, EditEntryActivity.class));
                }else{
                    Toast toast = new Toast(EditEntryDetailActivity.this);
                    toast.makeText(EditEntryDetailActivity.this, "Endtime is earlier than starttime!", Toast.LENGTH_LONG).show();
                }

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
                timeEntryService.remove(actualTimeEntry.getId());
                startActivity(new Intent(EditEntryDetailActivity.this, EditEntryActivity.class));
            }
        });


        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualTime = fromTime.getText().toString();
                int hour = Integer.parseInt(actualTime.substring(0,actualTime.indexOf(':')));
                int minute = Integer.parseInt(actualTime.substring(actualTime.indexOf(':')+1, actualTime.length()));
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEntryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualTime = toTime.getText().toString();
                int hour = Integer.parseInt(actualTime.substring(0,actualTime.indexOf(':')));
                int minute = Integer.parseInt(actualTime.substring(actualTime.indexOf(':')+1, actualTime.length()));
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEntryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        toTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualDate = fromDate.getText().toString();
                int year = Integer.parseInt(actualDate.substring(actualDate.lastIndexOf('.')+1, actualDate.length()));
                int month = Integer.parseInt(actualDate.substring(actualDate.indexOf('.')+1, actualDate.lastIndexOf('.')))-1;
                int day = Integer.parseInt(actualDate.substring(0, actualDate.indexOf('.')));
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEntryDetailActivity.this, new DatePickerDialog.OnDateSetListener(){
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

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualDate = toDate.getText().toString();
                int year = Integer.parseInt(actualDate.substring(actualDate.lastIndexOf('.')+1, actualDate.length()));
                int month = Integer.parseInt(actualDate.substring(actualDate.indexOf('.')+1, actualDate.lastIndexOf('.')))-1;
                int day = Integer.parseInt(actualDate.substring(0, actualDate.indexOf('.')));
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEntryDetailActivity.this, new DatePickerDialog.OnDateSetListener(){
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

    public void setDuration(long diff){
        diff = diff/1000;
        long hours = diff/3600;
        long min = (diff%3600)/60;
        TextView duration = (TextView) findViewById(R.id.textView_editEntryDetail_duration);
        duration.setText(String.format("%02d:%02d", hours, min));
    }

    public void setDuration(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        EditText editTextFromDay = (EditText) findViewById(R.id.eText_editEntryDetail_fromDate);
        EditText editTextFromTime = (EditText) findViewById(R.id.eText_editEntryDetail_fromTime);
        EditText editTextToDay = (EditText) findViewById(R.id.eText_editEntryDetail_toDate);
        EditText editTextToTime = (EditText) findViewById(R.id.eText_editEntryDetail_toTime);
        String fromDate = editTextFromDay.getText().toString();
        String fromTime = editTextFromTime.getText().toString();
        String toDate = editTextToDay.getText().toString();
        String toTime = editTextToTime.getText().toString();
        Date newFrom = null;
        Date newTo = null;
        try {
            newFrom = dateFormat.parse(fromDate + " " + fromTime);
            newTo =  dateFormat.parse(toDate + " " + toTime);
        }catch(Exception ex){};

        long diff = newTo.getTime() - newFrom.getTime();
        setDuration(diff);
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
