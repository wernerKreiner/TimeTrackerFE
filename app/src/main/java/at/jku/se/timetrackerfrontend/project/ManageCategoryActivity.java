package at.jku.se.timetrackerfrontend.project;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import at.jku.se.timetrackerfrontend.user.LoginActivity;
import at.jku.se.timetrackerfrontend.R;
import at.jku.se.timetrackerfrontend.user.SettingsActivity;
import at.jku.se.timetrackerfrontend.report.ProjectReportActivity;
import at.jku.se.timetrackerfrontend.report.UserReportActivity;
import at.jku.se.timetrackerfrontend.timeEntry.AutoEntryActivity;
import at.jku.se.timetrackerfrontend.timeEntry.EditEntryActivity;
import at.jku.se.timetrackerfrontend.timeEntry.ManualEntryActivity;
import entities.Category;
import services.CategoryService;

public class ManageCategoryActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);


        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setSubtitle("Categories");

        Bundle bundle = getIntent().getExtras();
        String projName = bundle.getString("ProjectName");

        CategoryService categoryService = new CategoryService();

        final ListView listview = (ListView) findViewById(R.id.listCategories);

        List<Category> list = new ArrayList (categoryService.get());
        list = list.stream().filter(x->x.getProject().getName().equals(projName)).collect(Collectors.toList());
        ArrayList categoriesList = new ArrayList(list);

        final CategoriesAdapter categoryAdapter= new CategoriesAdapter(this, categoriesList);
        listview.setAdapter(categoryAdapter);

        TextView titel = (TextView) findViewById(R.id.textTitel);
        titel.setText(projName);

        final FloatingActionButton btnFloatingAdd = (FloatingActionButton) findViewById(R.id.btnFlotingAdd);
        btnFloatingAdd.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               FragmentManager fm = getFragmentManager();
               DialogFragment dialogFragment = new ManageCategoryEditDialogFragment();
               Bundle args = new Bundle();
               args.putString("project", projName);
               dialogFragment.setArguments(args);
               dialogFragment.show(fm, "HEADER");
           }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManageProjectActivity.class));
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
