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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import entities.Category;
import entities.Cooperation;
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

        //initialise Services
        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();

        //get Timeentry from EditEntryActivity
        Bundle bundle = getIntent().getExtras();
        long timeEntryId = bundle.getLong("timeEntry");
        TimeEntry actualTimeEntry = timeEntryService.getById(timeEntryId);

        //fill fields from layout with Entrydata
        final Button btnOk = (Button) findViewById(R.id.btn_editEntry_ok);
        final Button btnCancel = (Button) findViewById(R.id.btn_editEntry_cancel);
        final Button btnDeleteEntry = (Button) findViewById(R.id.btn_editEntry_delete);

        EditText eTextNotice = (EditText) findViewById(R.id.eText_editEntryDetail_notice);
        eTextNotice.setText(actualTimeEntry.getNote());

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        EditText eTextFromDate = (EditText) findViewById(R.id.eText_editEntryDetail_fromDate);
        eTextFromDate.setText(dateFormat.format(actualTimeEntry.getFrom()));
        EditText eTextFromTime = (EditText) findViewById(R.id.eText_editEntryDetail_fromTime);
        eTextFromTime.setText(timeFormat.format(actualTimeEntry.getFrom()));
        EditText eTextToDate = (EditText) findViewById(R.id.eText_editEntryDetail_toDate);
        eTextToDate.setText(dateFormat.format(actualTimeEntry.getTo()));
        EditText eTextToTime = (EditText) findViewById(R.id.eText_editEntryDetail_toTime);
        eTextToTime.setText(timeFormat.format(actualTimeEntry.getTo()));

        setDuration(actualTimeEntry.getTo().getTime()- actualTimeEntry.getFrom().getTime());

        Category actualCategory;
        Project actualProject;

        //get category and project
        if(actualTimeEntry.getCategory() != null) {
            actualCategory = actualTimeEntry.getCategory();
            actualProject = actualCategory.getProject();
        }else{
            actualCategory = null;
            actualProject = null;
        }

        //initialise projectspinner
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

        int spnProjectIndex = 0;
        int i = 0;
        for(Project p : projectList){
            if(actualCategory != null && p == actualProject){
                spnProjectIndex = i;
            }
            i++;
        }


        AppCompatSpinner spnProject = (AppCompatSpinner) findViewById(R.id.spinner_editEntry_projectSelection);

        ArrayAdapter<Project> spnAdptProject = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_dropdown_item, projectList);
        spnProject.setAdapter(spnAdptProject);
        spnProject.setSelection(spnProjectIndex);

        //initialise categoryList and categoryspinner
        List<Category> categoryList = new LinkedList();
        categoryList.add(new Category("",0,null));

        AppCompatSpinner spnCategory = (AppCompatSpinner) findViewById(R.id.spinner_editEntry_categorySelection);

        ArrayAdapter<Category> spnAdptCategory = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        spnCategory.setAdapter(spnAdptCategory);

        spnProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                int spnCategoryIndex;
                List<Category> categoryListAll = categoryService.get();
                List<Category> categoryList = new LinkedList();
                Project selectedProject = (Project) parentView.getSelectedItem();
                categoryList.add(new Category("",0,null));
                spnCategoryIndex = 0;
                int i = 0;
                if(!selectedProject.getName().equals("")) {
                    if(actualProject != null && actualProject == selectedProject) {
                        for (Category c : categoryListAll) {
                            if (c.getProject() == actualProject) {
                                categoryList.add(c);
                                i++;
                                if (c == actualCategory) {
                                    spnCategoryIndex = i;
                                }
                            }
                        }
                    }else{
                        for (Category c : categoryListAll){
                            if (c.getProject() == selectedProject) {
                                categoryList.add(c);
                            }
                        }
                    }
                }
                spnAdptCategory.clear();
                spnAdptCategory.addAll(categoryList);
                spnAdptCategory.notifyDataSetChanged();
                spnCategory.setSelection(spnCategoryIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){

            }
        });

        //buttonlistener
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String fromDate = eTextFromDate.getText().toString();
                String fromTime = eTextFromTime.getText().toString();
                String toDate = eTextToDate.getText().toString();
                String toTime = eTextToTime.getText().toString();
                Date newFrom = null;
                Date newTo = null;
                try {
                    newFrom = dateFormat.parse(fromDate + " " + fromTime);
                    newTo =  dateFormat.parse(toDate + " " + toTime);
                }catch(Exception ex){};

                if(newFrom.getTime() <= newTo.getTime()){
                    actualTimeEntry.setFrom(newFrom);
                    actualTimeEntry.setTo(newTo);

                    Category newCategory = (Category) spnCategory.getSelectedItem();
                    if(newCategory.getName().equals("")) {
                        newCategory = null;
                    }
                    actualTimeEntry.setCategory(newCategory);
                    actualTimeEntry.setNote(eTextNotice.getText().toString());

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


        eTextFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualTime = eTextFromTime.getText().toString();
                int hour = Integer.parseInt(actualTime.substring(0,actualTime.indexOf(':')));
                int minute = Integer.parseInt(actualTime.substring(actualTime.indexOf(':')+1, actualTime.length()));
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEntryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eTextFromTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        eTextToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualTime = eTextToTime.getText().toString();
                int hour = Integer.parseInt(actualTime.substring(0,actualTime.indexOf(':')));
                int minute = Integer.parseInt(actualTime.substring(actualTime.indexOf(':')+1, actualTime.length()));
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEntryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eTextToTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        setDuration();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        eTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualDate = eTextFromDate.getText().toString();
                int year = Integer.parseInt(actualDate.substring(actualDate.lastIndexOf('.')+1, actualDate.length()));
                int month = Integer.parseInt(actualDate.substring(actualDate.indexOf('.')+1, actualDate.lastIndexOf('.')))-1;
                int day = Integer.parseInt(actualDate.substring(0, actualDate.indexOf('.')));
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEntryDetailActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay){
                        eTextFromDate.setText(String.format("%02d.%02d.%04d",selectedDay,selectedMonth+1,selectedYear));
                        setDuration();
                    }
                }, year, month, day);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        eTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String actualDate = eTextToDate.getText().toString();
                int year = Integer.parseInt(actualDate.substring(actualDate.lastIndexOf('.')+1, actualDate.length()));
                int month = Integer.parseInt(actualDate.substring(actualDate.indexOf('.')+1, actualDate.lastIndexOf('.')))-1;
                int day = Integer.parseInt(actualDate.substring(0, actualDate.indexOf('.')));
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEntryDetailActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay){
                        eTextToDate.setText(String.format("%02d.%02d.%04d",selectedDay,selectedMonth+1,selectedYear));
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
        EditText eTextFromDay = (EditText) findViewById(R.id.eText_editEntryDetail_fromDate);
        EditText eTextFromTime = (EditText) findViewById(R.id.eText_editEntryDetail_fromTime);
        EditText eTextToDay = (EditText) findViewById(R.id.eText_editEntryDetail_toDate);
        EditText eTextToTime = (EditText) findViewById(R.id.eText_editEntryDetail_toTime);
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
