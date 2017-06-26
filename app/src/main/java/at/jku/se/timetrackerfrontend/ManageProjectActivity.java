package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import entities.Category;
import entities.Cooperation;
import entities.Person;
import entities.Project;
import entities.ProjectRole;
import services.CategoryService;
import services.CooperationService;
import services.ProjectService;

public class ManageProjectActivity extends AppCompatActivity {

    String projName;
    Project project;
    ProjectRole projectRole;
    Optional<Cooperation> cooperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_project);

        ProjectService projectService = new ProjectService();
        CooperationService cooperationService = new CooperationService();
        Person currentUser = LoginActivity.user;

        TextView estiTime = (TextView) findViewById(R.id.estimateTimeTextView);
        TextView descrption = (TextView) findViewById(R.id.descriptionTextView);

        Button categorieButton = (Button) findViewById(R.id.btn_manageProject_categories);
        Button projectteam = (Button) findViewById(R.id.btn_manageProject_projectteam);
        Button remove = (Button) findViewById(R.id.btn_manageProject_removeProject);

        List<Project> projects =  projectService.get();

        List<String> projNames = new ArrayList<>();
        for (Project p : projects) {
            if(p.getName().compareTo("NULL") != 0)
            projNames.add(p.getName());
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerProj);
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projNames);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);


        class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projName = parent.getSelectedItem().toString();
                project = (projectService.get().stream().filter(x->x.getName().equals(projName)).findAny()).get();

                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getByProject(project);

                double time = categories.stream().mapToDouble(x->x.getEstimatedTime()).sum();
                estiTime.setText("" + time);
                descrption.setText(project.getDescription().toString());

                if(projName != null) {
                    cooperation = cooperationService.getByPerson(currentUser).stream().filter(x -> x.getProject().equals(project)).findAny();

                    if (cooperation.isPresent() && cooperation.get().getProjectRole().equals(ProjectRole.COWORKER)) {
                        projectteam.setEnabled(false);
                        categorieButton.setEnabled(false);
                        remove.setEnabled(false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

        spinner.setOnItemSelectedListener(new myOnItemSelectedListener());

        if(!projectteam.isEnabled()){
            projectteam.setBackgroundColor(Color.DKGRAY);
        }
        categorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = (new Intent(ManageProjectActivity.this, ManageCategoryActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString("ProjectName", projName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        projectteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProjectActivity.this, ManageProjectTeamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ProjectName", projName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projNames.size() > 0) {
                    projectService.remove(project.getId());
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
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
