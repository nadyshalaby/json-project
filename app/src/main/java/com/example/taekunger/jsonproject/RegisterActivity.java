package com.example.taekunger.jsonproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    final protected static String TAG = MoviesActivity.class.getSimpleName();
    protected String url = "http://haladoctor.com/testSec/register.php";
    protected EditText email_txt, password_txt, fname_txt, lname_txt;
    protected Button register_btn;
    protected String params = "";
    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_txt = (EditText) findViewById(R.id.register_email_txt);
        password_txt = (EditText) findViewById(R.id.register_password_txt);
        fname_txt = (EditText) findViewById(R.id.register_fname_txt);
        lname_txt = (EditText) findViewById(R.id.register_lname_txt);
        register_btn = (Button) findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = "fname=" + fname_txt.getText() +
                        "&lname=" + lname_txt.getText() +
                        "&email=" + email_txt.getText() +
                        "&password=" + password_txt.getText();


                new RegisterTask().execute();
            }
        });

    }

    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(RegisterActivity.this);
            progress.setMessage("Loading...");
            progress.show();
        }


        @Override
        protected Boolean doInBackground(Void... args) {
            final String response = JSONHttpRequest.makeRequest(url, JSONHttpRequest.POST, params);
//            Log.i(LoginActivity.TAG, params + "Response: " + response);
            try {

                JSONObject obj = new JSONObject(response);
                int status = obj.getInt("status");
                if (status == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Successful Register!", Toast.LENGTH_LONG).show();
                        }
                    });
                    return true;
                } else {
                    final String message = obj.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            progress.dismiss();
        }

    }
}
