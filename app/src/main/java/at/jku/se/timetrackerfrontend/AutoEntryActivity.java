package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class AutoEntryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_entry);

        final Button btnStartTimer = (Button) findViewById(R.id.btn_autoEntry_start);
        final Button btnCancel = (Button) findViewById(R.id.btn_autoEntry_cancel);

        final Chronometer focus = (Chronometer) findViewById(R.id.chronometer);

        final Spinner spnProject = (Spinner) findViewById(R.id.spinner_autoEntry_projectSelection);
        String[] projects = new String[]{"","PR SE", "PR SE Prototyp", "KT CE"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projects);
        spnProject.setAdapter(spinnerAdapter);

        final Spinner spnCategory = (Spinner) findViewById(R.id.spinner_autoEntry_categorySelection);
        String[] categories = new String[]{"","Entwurf", "Prototyp", "Doku"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spnCategory.setAdapter(spinnerAdapter2);

        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStartTimer.getText().equals("Start")){
                    focus.setBase(SystemClock.elapsedRealtime());
                    focus.start();
                    btnStartTimer.setText("Stop");
                }else{
                    focus.stop();
                    startActivity(new Intent(AutoEntryActivity.this, EditEntryDetailActivity.class));
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
