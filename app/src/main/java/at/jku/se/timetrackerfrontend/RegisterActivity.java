package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import entities.Person;
import services.PersonService;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PersonService personService = new PersonService();

        EditText firstName = (EditText) findViewById(R.id.eText_register_firstname);
        EditText surname = (EditText) findViewById(R.id.eText_register_surname);
        EditText nickname = (EditText) findViewById(R.id.eText_register_nickname);
        EditText email = (EditText) findViewById(R.id.eText_register_email);
        EditText password = (EditText) findViewById(R.id.eText_register_password);
        EditText confirmPassword = (EditText) findViewById(R.id.eText_register_passwordconfirm);



        Button cancel = (Button) findViewById(R.id.btn_register_cancel);
        Button register = (Button) findViewById(R.id.btn_register_register);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailExists = personService.get().stream().anyMatch(x->x.getEmail().toString().equals(email.getText().toString()));
                boolean correctPassword;

                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    correctPassword = true;
                }else {
                    correctPassword = false;
                }
                if(emailExists) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You are already a User :) ", Toast.LENGTH_LONG);
                    toast.show();
                }else if(!correctPassword){
                    Toast toast = Toast.makeText(getApplicationContext(), "Password does not match to confirm password", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    Person p = new Person(firstName.getText().toString(), surname.getText().toString(), nickname.getText().toString(), email.getText().toString(), password.getText().toString(), true);
                    //edit werner wegen personvariable in loginactivityl
                    p = personService.create(p);
                    LoginActivity.user = p;
                    //ende edit
                    startActivity(new Intent(RegisterActivity.this, EditEntryActivity.class));
                }
            }
        });
    }
}
