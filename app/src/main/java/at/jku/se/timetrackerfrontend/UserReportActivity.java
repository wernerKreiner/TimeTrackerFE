package at.jku.se.timetrackerfrontend;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UserReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);

        Spinner dropdown = (Spinner) findViewById(R.id.spinner_userReport_projectSelection);
        String[] projects = new String[]{"PR SE", "PR SE Prototyp", "KT CE"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projects);
        dropdown.setAdapter(spinnerAdapter);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_userReport_table);

        TableRow trow = new TableRow(this);
        TextView date = new TextView(this);
        date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        date.setText("25.03.2017 ");
        date.setTextSize(15);
        TextView from = new TextView(this);
        from.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        from.setText("10:00 ");
        from.setTextSize(15);
        TextView to = new TextView(this);
        to.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        to.setText("16:00 ");
        to.setTextSize(15);
        TextView duration = new TextView(this);
        duration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        duration.setText("06:00 ");
        duration.setTextSize(15);
        TextView project = new TextView(this);
        project.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        project.setText("PR SE ");
        project.setTextSize(15);
        TextView category = new TextView(this);
        category.setText("Prototyp");
        category.setTextSize(15);
        trow.addView(date);
        trow.addView(from);
        trow.addView(to);
        trow.addView(duration);
        trow.addView(project);
        trow.addView(category);
        tableLayout.addView(trow);

        TableRow trow2 = new TableRow(this);
        TextView date2 = new TextView(this);
        date2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        date2.setText("Summe ");
        date2.setTextSize(18);
        TextView from2 = new TextView(this);
        from2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        from2.setText("");
        from2.setTextSize(18);
        TextView to2 = new TextView(this);
        to2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        to2.setText("");
        to2.setTextSize(18);
        TextView duration2 = new TextView(this);
        duration2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        duration2.setText("06:00 ");
        duration2.setTextSize(18);
        TextView project2 = new TextView(this);
        project2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        project2.setText("");
        project2.setTextSize(18);
        TextView category2 = new TextView(this);
        category2.setText("");
        category2.setTextSize(18);
        trow2.addView(date2);
        trow2.addView(from2);
        trow2.addView(to2);
        trow2.addView(duration2);
        trow2.addView(project2);
        trow2.addView(category2);
        tableLayout.addView(trow2);
    }
}
