package at.jku.se.timetrackerfrontend;

//import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ManageCategoryActivity extends AppCompatActivity {

    private final String projectName = "TestProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        TextView textView = (TextView) findViewById(R.id.tVProjetname);
        textView.setText(this.projectName);

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
