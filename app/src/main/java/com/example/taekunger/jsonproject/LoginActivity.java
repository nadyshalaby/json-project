package com.example.taekunger.jsonproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    final protected static String TAG = LoginActivity.class.getSimpleName();
    protected Button loginBtn, registerBtn;
    protected EditText email, password;
    protected String params = "", url = "http://haladoctor.com/testSec/login.php";
    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);

        email = (EditText) findViewById(R.id.login_email_txt);
        password = (EditText) findViewById(R.id.login_password_txt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                params = "email=" + email.getText() + "&&password=" + password.getText();

                new LoginTask().execute();
            }
        });

        registerBtn = (Button) findViewById(R.id.move_to_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


    private class LoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Loading...");
            progress.show();
        }


        @Override
        protected Boolean doInBackground(Void... args) {
            final String response = JSONHttpRequest.makeRequest(url, JSONHttpRequest.POST, params);

            try {

                JSONObject obj = new JSONObject(response);
                int status = obj.getInt("status");
                if (status == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
                        }
                    });
                    return true;
                } else {
                    final String message = obj.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
                    return false;
                }
            } catch (Exception e) {
                Log.e(LoginActivity.TAG, "Exception: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                startActivity(new Intent(LoginActivity.this, ChoiceActivity.class));
            }

            progress.dismiss();
        }

    }


}
