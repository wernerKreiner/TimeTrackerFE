package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProjectReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_report);

        Spinner dropdown = (Spinner) findViewById(R.id.spinner_projectReport_projectSelection);
        String[] projects = new String[]{"PR SE", "PR SE Prototyp", "KT CE"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projects);
        dropdown.setAdapter(spinnerAdapter);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_projectReport_table);

        TableRow trow = new TableRow(this);
        TextView tViewCategory = new TextView(this);
        tViewCategory.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewCategory.setText("Planen ");
        tViewCategory.setTextSize(15);
        TextView tViewEstTime = new TextView(this);
        tViewEstTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewEstTime.setText("10:00 ");
        tViewEstTime.setTextSize(15);
        TextView tViewTime = new TextView(this);
        tViewTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewTime.setText("06:00 ");
        tViewTime.setTextSize(15);
        TextView tViewDifference = new TextView(this);
        tViewDifference.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewDifference.setText("-04:00 ");
        tViewDifference.setTextSize(15);
        trow.addView(tViewCategory);
        trow.addView(tViewEstTime);
        trow.addView(tViewTime);
        trow.addView(tViewDifference);
        tableLayout.addView(trow);

        TableRow trow2 = new TableRow(this);
        TextView tViewCategory2 = new TextView(this);
        tViewCategory2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewCategory2.setText("Design ");
        tViewCategory2.setTextSize(15);
        TextView tViewEstTime2 = new TextView(this);
        tViewEstTime2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewEstTime2.setText("15:00 ");
        tViewEstTime2.setTextSize(15);
        TextView tViewTime2 = new TextView(this);
        tViewTime2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewTime2.setText("16:00 ");
        tViewTime2.setTextSize(15);
        TextView tViewDifference2 = new TextView(this);
        tViewDifference2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewDifference2.setText("+01:00 ");
        tViewDifference2.setTextSize(15);
        trow2.addView(tViewCategory2);
        trow2.addView(tViewEstTime2);
        trow2.addView(tViewTime2);
        trow2.addView(tViewDifference2);
        tableLayout.addView(trow2);

        TableRow trow3 = new TableRow(this);
        TextView tViewCategory3 = new TextView(this);
        tViewCategory3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewCategory3.setText("Implementierung ");
        tViewCategory3.setTextSize(15);
        TextView tViewEstTime3 = new TextView(this);
        tViewEstTime3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewEstTime3.setText("57:00 ");
        tViewEstTime3.setTextSize(15);
        TextView tViewTime3 = new TextView(this);
        tViewTime3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewTime3.setText("55:00 ");
        tViewTime3.setTextSize(15);
        TextView tViewDifference3 = new TextView(this);
        tViewDifference3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewDifference3.setText("-02:00 ");
        tViewDifference3.setTextSize(15);
        trow3.addView(tViewCategory3);
        trow3.addView(tViewEstTime3);
        trow3.addView(tViewTime3);
        trow3.addView(tViewDifference3);
        tableLayout.addView(trow3);

        TableRow trow4 = new TableRow(this);
        TextView tViewCategory4 = new TextView(this);
        tViewCategory4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewCategory4.setText("Testen ");
        tViewCategory4.setTextSize(15);
        TextView tViewEstTime4 = new TextView(this);
        tViewEstTime4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewEstTime4.setText("30:00 ");
        tViewEstTime4.setTextSize(15);
        TextView tViewTime4 = new TextView(this);
        tViewTime4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewTime4.setText("20:00 ");
        tViewTime4.setTextSize(15);
        TextView tViewDifference4 = new TextView(this);
        tViewDifference4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewDifference4.setText("-10:00 ");
        tViewDifference4.setTextSize(15);
        trow4.addView(tViewCategory4);
        trow4.addView(tViewEstTime4);
        trow4.addView(tViewTime4);
        trow4.addView(tViewDifference4);
        tableLayout.addView(trow4);

        TableRow trow5 = new TableRow(this);
        TextView tViewCategory5 = new TextView(this);
        tViewCategory5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewCategory5.setText("Summe ");
        tViewCategory5.setTextSize(18);
        TextView tViewEstTime5 = new TextView(this);
        tViewEstTime5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewEstTime5.setText("112:00 ");
        tViewEstTime5.setTextSize(18);
        TextView tViewTime5 = new TextView(this);
        tViewTime5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewTime5.setText("97:00 ");
        tViewTime5.setTextSize(18);
        TextView tViewDifference5 = new TextView(this);
        tViewDifference5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tViewDifference5.setText("-15:00 ");
        tViewDifference5.setTextSize(18);
        trow5.addView(tViewCategory5);
        trow5.addView(tViewEstTime5);
        trow5.addView(tViewTime5);
        trow5.addView(tViewDifference5);
        tableLayout.addView(trow5);
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
        } else if (id == R.id.manageTeam) {
            startActivity(new Intent(this, ManageProjectTeamActivity.class));
        } else if (id == R.id.manageProj) {
            startActivity(new Intent(this, ManageProjectActivity.class));
        } else if (id == R.id.manageCateg) {
            startActivity(new Intent(this, ManageCategoryActivity.class));
        } else if (id == R.id.projReport) {
            startActivity(new Intent(this, ProjectReportActivity.class));
        } else if (id == R.id.userReport) {
            startActivity(new Intent(this, UserReportActivity.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return true;
    }

}