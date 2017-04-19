package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import entities.Person;
import services.PersonService;


public class SettingsActivity extends AppCompatActivity {

    boolean correctPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        EditText firstname = (EditText) findViewById(R.id.eText_settings_firstname);
        EditText surname = (EditText) findViewById(R.id.eText_settings_surname);
        EditText email = (EditText) findViewById(R.id.eText_settings_email);
        EditText nickname = (EditText) findViewById(R.id.eText_settings_nickname);
        EditText password = (EditText) findViewById(R.id.eText_settings_password);
        EditText confirmpassword = (EditText) findViewById(R.id.eText_settings_passwordconfirm);
        CheckBox quickstart = (CheckBox) findViewById(R.id.checkBox_settings_quickstart);

        PersonService personService = new PersonService();

        //edit Werner wegen eingeloggten User
        //Person person = personService.get().stream().findFirst().get();
        Person person = LoginActivity.user;

        //ende edit

        firstname.setText(person.getFirstname());
        surname.setText(person.getLastname());
        email.setText(person.getEmail());
        nickname.setText(person.getNickname());
        password.setText(person.getPassword());
        confirmpassword.setText(person.getPassword());
        quickstart.setChecked(person.getQuickstart());

        Button cancel = (Button) findViewById(R.id.btn_settings_cancel);
        Button apply = (Button) findViewById(R.id.btn_settings_apply);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, EditEntryActivity.class));
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(confirmpassword.getText().toString())) {
                    correctPassword = true;
                }else {
                    correctPassword = false;
                }
                if(!correctPassword){
                    Toast toast = Toast.makeText(getApplicationContext(), "Password does not match to confirm password", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    person.setFirstname(firstname.getText().toString());
                    person.setLastname(surname.getText().toString());
                    person.setNickname(nickname.getText().toString());
                    person.setEmail(email.getText().toString());
                    person.setPassword(password.getText().toString());
                    person.setQuickstart(quickstart.isChecked());
                    startActivity(new Intent(SettingsActivity.this, EditEntryActivity.class));
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
