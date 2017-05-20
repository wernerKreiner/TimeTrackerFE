package at.jku.se.timetrackerfrontend;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import entities.*;
import services.CooperationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ManageProjectTeamActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_project_team);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setSubtitle("Project Team");

        Bundle bundle = getIntent().getExtras();
        String projName = bundle.getString("ProjectName");

        CooperationService cooperationService = new CooperationService();

        final ListView listview = (ListView) findViewById(R.id.members);

        List<Cooperation> list = new ArrayList<>(cooperationService.get());
        list = list.stream().filter(x->x.getProject().getName().equals(projName)).collect(Collectors.toList());

        ArrayList coopList = new ArrayList(list);

        final UserAdapter adapter = new UserAdapter(this, coopList);
        listview.setAdapter(adapter);

        TextView titel = (TextView) findViewById(R.id.textTitel);
        titel.setText(projName);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingBtnAddMember);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                android.app.DialogFragment dialogFragment = new ManageProjectTeamEditDialogFragment();
                Bundle args = new Bundle();
                args.putString("project", projName);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "HEADER");
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
