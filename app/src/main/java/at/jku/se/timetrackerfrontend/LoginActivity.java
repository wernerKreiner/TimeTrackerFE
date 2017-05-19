
package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import entities.Person;
import services.PersonService;

public class LoginActivity extends AppCompatActivity {
    //get enteties and services
    public static Person user; // = found person
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize enteties and services
        personService = new PersonService();

        //get Views
        final EditText email = (EditText) findViewById(R.id.eText_loginView_email); //input Email
        final EditText password = (EditText) findViewById(R.id.eText_loginView_password); //input password
        final Button btnLogin = (Button) findViewById(R.id.btn_loginView_login);
        final Button btnRegister = (Button) findViewById(R.id.btn_loginView_register);

        //Quicklogin
        email.setText("werni@jku.at");
        password.setText("12345");


        //request focus on email  edit werner
        email.requestFocus();

        //Event for Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //find person by email
                List<Person> personList = personService.get();
                Person findUser = null; //null statt new Person()
                boolean matchPerson = false;
                for(Person p : personList) {
                    String curEmail = email.getText().toString();
                    String curPEmail = p.getEmail();
                    if (curPEmail.equals(curEmail)) { //mehr Suchkriterien ???
                        findUser = p;
                        matchPerson = true;
                        break;
                    } //Ende if
                } //Ende for

                if (!matchPerson) { //User not found
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(LoginActivity.this, "The e-mail is wrong!Please try again or register.", Toast.LENGTH_LONG).show();
                } else {
                    //does password match?
                    boolean passwordMatch = false;
                    String curPassword = password.getText().toString();
                    String curPPassword = findUser.getPassword();
                    if (curPassword.equals(curPPassword)) {
                        passwordMatch = true;
                    }

                    if (!passwordMatch) {
                        Toast toast = new Toast(LoginActivity.this);
                        toast.makeText(LoginActivity.this, "Wrong password!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        user = findUser;
                        startActivity(new Intent(LoginActivity.this, EditEntryActivity.class)); //login succsesfull, go to next activity
                    }
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

/*
package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.List;

import entities.Person;
import services.PersonService;

public class LoginActivity extends AppCompatActivity {
    //get enteties and services
    public static Person user; // = found person
    PersonService personService;

    public static Person user;
    PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton);
        final EditText email = (EditText) findViewById(R.id.emailInput);
        email.setText("test@jku.at");
        final EditText password = (EditText) findViewById(R.id.passwordInput);
        password.setText("1234");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("test@jku.at") && password.getText().toString().equals("1234")){
                    startActivity(new Intent(LoginActivity.this, EditEntryActivity.class));
                }else {
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(LoginActivity.this, "Not a member yet? Yout can register now!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

*/