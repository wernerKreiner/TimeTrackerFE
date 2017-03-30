package at.jku.se.timetrackerfrontend;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import entities.Category;
import entities.Measurement;
import entities.TimeEntry;
import entities.User;

public class EditEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        final ListView listview = (ListView) findViewById(R.id.entries);
        TimeEntry[] values = new TimeEntry[]{new TimeEntry(1, "Implementation", new Date(20, 03, 2017), new Date(22, 03, 2017), "blabla", Measurement.MANUAL, new User(), new Category()),
                new TimeEntry(2, "Organisation", new Date(20, 03, 2017), new Date(22, 03, 2017), "blabla", Measurement.MANUAL, new User(), new Category())};

        final ArrayList<TimeEntry> list = new ArrayList<TimeEntry>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final EntryAdapter adapter = new EntryAdapter(this,
                list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(EditEntryActivity.this, EditEntryDetailActivity.class));
                    //FragmentManager fm = getFragmentManager();
                //   android.app.DialogFragment dialogFragment = new EditTimeEntryFragment();
                //dialogFragment.show(fm, "HEADER");

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
