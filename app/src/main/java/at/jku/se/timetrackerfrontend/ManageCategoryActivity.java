package at.jku.se.timetrackerfrontend;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        final ListView listView = (ListView) findViewById(R.id.listCategories);
        String [] values = {"Organisation", "Entwicklung", "Testdaten erzeugen"};

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.content_listview_categories, R.id.categorieName, list);
        listView.setAdapter(adapter);

        final FloatingActionButton btnFloatingAdd = (FloatingActionButton) findViewById(R.id.btnFlotingAdd);
        btnFloatingAdd.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                android.app.DialogFragment dialogFragment = new ManageCategoryEditDialogFragment();
                dialogFragment.show(fm, "HEADER");
           }
        });
    }
}
