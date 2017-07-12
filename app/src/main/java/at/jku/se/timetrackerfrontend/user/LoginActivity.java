
package at.jku.se.timetrackerfrontend.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import at.jku.se.timetrackerfrontend.R;
import at.jku.se.timetrackerfrontend.timeEntry.AutoEntryActivity;
import at.jku.se.timetrackerfrontend.timeEntry.EditEntryActivity;
import entities.Person;
import services.PersonService;

public class LoginActivity extends AppCompatActivity {

    boolean quicklogin = true;
    public static Person user;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize entities and services
        personService = new PersonService();

        //get Views
        final EditText email = (EditText) findViewById(R.id.eText_loginView_email); //input Email
        final EditText password = (EditText) findViewById(R.id.eText_loginView_password); //input password
        final Button btnLogin = (Button) findViewById(R.id.btn_loginView_login);
        final Button btnRegister = (Button) findViewById(R.id.btn_loginView_register);

        //Quicklogin used for developing
        if(quicklogin) {
            email.setText("werni@jku.at");
            password.setText("12345");
        }

        //request focus on email
        email.requestFocus();

        //Event for Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curEmail = email.getText().toString();
                Person p = personService.getByEmail(curEmail);
                if(p != null) {
                    //person found
                    String curPasswort = password.getText().toString();
                    if(curPasswort.equals(p.getPassword())){
                        //password correct
                        user = p;
                        if(user.getQuickstart()){
                            startActivity(new Intent(LoginActivity.this, AutoEntryActivity.class));
                        }else{
                            startActivity(new Intent(LoginActivity.this, EditEntryActivity.class));
                        }
                    }else{
                        Toast toast = new Toast(LoginActivity.this);
                        toast.makeText(LoginActivity.this, "Wrong password!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(LoginActivity.this, "The e-mail is wrong!Please try again or register.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Event for Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
