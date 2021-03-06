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
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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
import services.CooperationService;
import services.PersonService;
import services.ProjectService;
import services.TimeEntryService;

public class AutoEntryActivity extends AppCompatActivity{

    TimeEntryService timeEntryService;
    ProjectService projectService;
    CategoryService categoryService;
    PersonService personService;
    CooperationService cooperationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_entry);

        //initialise Services
        timeEntryService = new TimeEntryService();
        projectService = new ProjectService();
        categoryService = new CategoryService();
        personService = new PersonService();
        cooperationService = new CooperationService();

        final Button btnStartTimer = (Button) findViewById(R.id.btn_autoEntry_start);
        final Button btnCancel = (Button) findViewById(R.id.btn_autoEntry_cancel);
        final Chronometer focus = (Chronometer) findViewById(R.id.chronometer);

        //initialise projectList and projectspinner
        List<Project> projectList = new LinkedList<Project>();
        List<Project> projectListAll = projectService.get();
        projectList.add(new Project("",""));

        for(Project p : projectListAll){
            List<Cooperation> cooperationList = cooperationService.getByProject(p);
            for(Cooperation c : cooperationList){
                if(c.getPerson().getId() == LoginActivity.user.getId() && c.getProject().getId() == p.getId()){
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
                        if(c.getProject().getId() == selectedProject.getId()){
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

                    //round time entry
                    RadioGroup rg = (RadioGroup) findViewById(R.id.radioGr_autoEntry) ;

                    RadioButton round5 = (RadioButton) findViewById(R.id.radioBtn_autoEntry_round5);
                    RadioButton round15 = (RadioButton) findViewById(R.id.radioBtn_autoEntry_round15);

                    if(round5.isChecked()) {
                        from[0] = roundTime5(from[0]);
                        to = roundTime5(to);
                    } else if(round15.isChecked()) {
                        from[0] = roundTime15(from[0]);
                        to = roundTime15(to);
                    }

                    // Time Alert
                    if (category != null) {
                        //create List with all TimeEntries of selected category
                        List<TimeEntry> timeEntryList = timeEntryService.get();
                        List<TimeEntry> timeEntryByCategoryList = new LinkedList<TimeEntry>();
                        for (TimeEntry te : timeEntryList) {
                            if (te.getCategory() != null && category.getId() == te.getCategory().getId()) {
                                timeEntryByCategoryList.add(te);
                            }
                        }

                        //calculate sum of existing TimeEntries
                        long sumExisting = 0;
                        for (TimeEntry te : timeEntryByCategoryList) {
                            long diff = te.getTo().getTime() - te.getFrom().getTime();
                            sumExisting = sumExisting + diff;
                        }

                        //add duration of actual timeEntry
                        sumExisting = sumExisting + to.getTime()-from[0].getTime();

                        //compare estimated time with existing time
                        long estimatedTime = (long) category.getEstimatedTime();
                        estimatedTime = estimatedTime*60*60*1000;
                        if (sumExisting >= estimatedTime) {
                            Toast toast = new Toast(AutoEntryActivity.this);
                            toast.makeText(AutoEntryActivity.this, "The estimated time of this category is already reached!", Toast.LENGTH_LONG).show();
                        }
                    }

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

    public Date roundTime(Date unrounded){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String fromString = sdf.format(unrounded);
        String minString = fromString.substring(14, 16); //"HH:mm" 3,5
        int min = Integer.parseInt(minString);
        String hourString = fromString.substring(11, 13); //"HH:mm" 0,2
        int hour = Integer.parseInt(hourString);
        String fromDate = fromString.substring(0, 10);

        if (0 <= min && min <= 7) {
            min = 0;
        } else if (8 <= min && min <= 22) {
            min = 15;
        } else if (23 <= min && min <= 37) {
            min = 30;
        } else if (38 <= min && min <= 52) {
            min = 45;
        } else if (53 <= min && min <= 59) {
            hour++;
            min = 0;
        }

        Date rounded = null;
        String fromTimeString = Integer.toString(hour) + ":" + Integer.toString(min);
        try {
            rounded = sdf.parse(fromDate + " " + fromTimeString);
        } catch (Exception ex) {
        }
        return rounded;
    }

    public Date roundTime15(Date unrounded){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String fromString = sdf.format(unrounded);
        String minString = fromString.substring(14, 16); //"HH:mm" 3,5
        int min = Integer.parseInt(minString);
        String hourString = fromString.substring(11, 13); //"HH:mm" 0,2
        int hour = Integer.parseInt(hourString);
        String fromDate = fromString.substring(0, 10);

        if (0 <= min && min <= 7) {
            min = 0;
        } else if (8 <= min && min <= 22) {
            min = 15;
        } else if (23 <= min && min <= 37) {
            min = 30;
        } else if (38 <= min && min <= 52) {
            min = 45;
        } else if (53 <= min && min <= 59) {
            hour++;
            min = 0;
        }

        Date rounded = null;
        String fromTimeString = Integer.toString(hour) + ":" + Integer.toString(min);
        try {
            rounded = sdf.parse(fromDate + " " + fromTimeString);
        } catch (Exception ex) {
        }
        return rounded;
    }

    public Date roundTime5(Date unrounded){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String fromString = sdf.format(unrounded);
        String minString = fromString.substring(14, 16); //"HH:mm" 3,5
        int min = Integer.parseInt(minString);
        String hourString = fromString.substring(11, 13); //"HH:mm" 0,2
        int hour = Integer.parseInt(hourString);
        String fromDate = fromString.substring(0, 10);

        if (0 <= min && min <= 2) {
            min = 0;
        }
        else if (3 <= min && min <= 7) {
            min = 5;
        }
        else if (8 <= min && min <= 12) {
            min = 10;
        }
        else if (13 <= min && min <= 17) {
            min = 15;
        }
        else if (18 <= min && min <= 22) {
            min=20;
        }
        else if (23 <= min && min <= 27) {
            min=25;
        }
        else if (28 <= min && min <= 32) {
            min=30;
        }
        else if (33 <= min && min <= 37) {
            min=35;
        }
        else if (38 <= min && min <= 42) {
            min=40;
        }
        else if (43 <= min && min <= 47) {
            min=45;
        }
        else if (48 <= min && min <= 52) {
            min=50;
        }
        else if (53 <= min && min <= 57) {
            min=55;
        }
        else if (58 <= min && min <= 59) {
            hour++;
            min=0;
        }

        Date rounded = null;
        String fromTimeString = Integer.toString(hour) + ":" + Integer.toString(min);
        try {
            rounded = sdf.parse(fromDate + " " + fromTimeString);
        } catch (Exception ex) {
        }
        return rounded;
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