package at.jku.se.timetrackerfrontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton);

        final EditText email = (EditText) findViewById(R.id.emailInput);
        final EditText password = (EditText) findViewById(R.id.passwordInput);

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
