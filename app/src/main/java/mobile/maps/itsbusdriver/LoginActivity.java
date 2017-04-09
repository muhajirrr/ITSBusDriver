package mobile.maps.itsbusdriver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnLogin;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        etUser = (EditText) findViewById(R.id.etUsername);
        etPass = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();

                if (username.equals("")) {
                    etUser.setError("Username harus diisi");
                } else if (password.equals("")) {
                    etPass.setError("Password harus diisi");
                } else {
                    if (username.equals("driver1") && password.equals("itsbusdriver1")) {
                        session.createLoginSession("1");

                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        finish();
                    } else if (username.equals("driver2") && password.equals("itsbusdriver2")) {
                        session.createLoginSession("2");

                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        finish();
                    } else if (username.equals("driver3") && password.equals("itsbusdriver3")) {
                        session.createLoginSession("3");

                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        finish();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Login failed");
                        alertDialog.setMessage("Username or password is incorrect");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

            }
        });
    }
}