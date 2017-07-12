package at.jku.se.timetrackerfrontend.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import at.jku.se.timetrackerfrontend.user.LoginActivity;
import at.jku.se.timetrackerfrontend.R;
import at.jku.se.timetrackerfrontend.user.SettingsActivity;
import at.jku.se.timetrackerfrontend.report.ProjectReportActivity;
import at.jku.se.timetrackerfrontend.report.UserReportActivity;
import at.jku.se.timetrackerfrontend.timeEntry.AutoEntryActivity;
import at.jku.se.timetrackerfrontend.timeEntry.EditEntryActivity;
import at.jku.se.timetrackerfrontend.timeEntry.ManualEntryActivity;
import entities.Cooperation;
import entities.Project;
import entities.ProjectRole;
import services.CooperationService;
import services.ProjectService;

public class CreateProjectActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        CooperationService cooperationService = new CooperationService();
        ProjectService projectService = new ProjectService();

        Button save = (Button) findViewById(R.id.btn_createProject_save);
        Button cancel = (Button) findViewById(R.id.btn_createProject_cancel);

        EditText name = (EditText) findViewById(R.id.eText_createProject_name);
        EditText descr = (EditText) findViewById(R.id.eText_createProject_description);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText() != null){
                    Project project = new Project(name.getText().toString(), descr.getText().toString());
                    project = projectService.create(project);
                    cooperationService.create(new Cooperation(ProjectRole.ADMIN, LoginActivity.user, project));
                    startActivity(new Intent(CreateProjectActivity.this, ManageProjectActivity.class));
                }else {
                    Toast toast = new Toast(CreateProjectActivity.this);
                    toast.makeText(CreateProjectActivity.this, "Give your Project a Name", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateProjectActivity.this, EditEntryActivity.class));
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
