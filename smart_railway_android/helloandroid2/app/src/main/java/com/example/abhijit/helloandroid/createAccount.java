package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.estimote.sdk.EstimoteSDK.getApplicationContext;
import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 1/31/2017.
 */
public class createAccount extends AppCompatActivity {

    databaseHelper mydb;
    EditText name, email, password, passconf, phone, address;
    Button create;
    TextView require;
    BackgroundTask backgroundTask = new BackgroundTask(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar5);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //    mydb = new databaseHelper(this);

        name = (EditText) findViewById(R.id.nameid);
        email = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.passid);
        passconf = (EditText) findViewById(R.id.conpassid);
        phone = (EditText) findViewById(R.id.contactid);
        address = (EditText) findViewById(R.id.addid);
        create = (Button) findViewById(R.id.saveid);
        require = (TextView) findViewById(R.id.requireid);
        storeProfile();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //Intent intent = new Intent(getApplicationContext(),Homepage.class);
            //startActivity(intent);

            finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void storeProfile()

    {
        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //String reg_url = "http://192.168.43.180/beacon/register.php";
                        String passccheck = passconf.getText().toString();
                        String passcheck = password.getText().toString();
                        String namecheck = name.getText().toString();
                        String emailcheck = email.getText().toString();
                        String phonecheck = phone.getText().toString();
                        String addcheck = address.getText().toString();
                        if (backgroundTask != null){
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(createAccount.this);
                        if (passccheck.length() == 0 || passcheck.length() == 0 || namecheck.length() == 0 || emailcheck.length() == 0 || phonecheck.length() == 0 || addcheck.length() == 0) {
                            require.setText("ALL FIELDS ARE REQUIRED. PLEASE FILL IN ALL THE DETAILS");
                        } else {
                            require.setText("");
                            if (passcheck.equals(passccheck)) {
                                if (validemail(emailcheck)) {
                                    //if (mydb.emailfind(emailcheck)) {
                                        if (validpass(passcheck)) {
                                            //String method="register";
                                            backgroundTask.execute(namecheck, emailcheck, phonecheck, passcheck, addcheck);


                                           // boolean res = mydb.insertit(emailcheck, namecheck, passcheck, addcheck, phonecheck);
                                            //if (res == true) {
                                            //    Toast.makeText(createAccount.this, "account created", Toast.LENGTH_LONG).show();
                                            //} else
                                             //   Toast.makeText(createAccount.this, "not created", Toast.LENGTH_LONG).show();


                                        } else {
                                            Toast.makeText(createAccount.this, "invalid password", Toast.LENGTH_LONG).show();
                                        }
                                    //} else {
                                      //  Toast.makeText(createAccount.this, "account with this email is already present", Toast.LENGTH_LONG).show();
                                    //}

                                } else {
                                    Toast.makeText(createAccount.this, "invalid email", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(createAccount.this, "passwords dont match", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );

    }

    public boolean validemail(String emailtake) {
        String getText = emailtake;

        String Expn =

                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";


        if (getText.matches(Expn) && getText.length() > 0)

        {
            return true;
        } else
            return false;
    }

    public static boolean validpass(final String passtake) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        if (passtake.length() > 8) {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(passtake);
            if (matcher.matches()) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        Context ctx;
        //  JSONObject object;
        //String method;
       // AlertDialog alertDialog;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        String reg_url = BASE_URL+"register.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // alertDialog = new AlertDialog.Builder(ctx).create();
           // alertDialog.setTitle("account status");
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String name = params[0];
                String email = params[1];
                String phone = params[2];
                String password = params[3];
                String address = params[4];
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((reg_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(reg_url + "\n");
                }
                String result = stringBuilder.toString();
                bufferedReader.close();

                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject object = new JSONObject(response);
                if ((object.optInt("code"))==200) {
                    Toast.makeText(createAccount.this, "registration successful", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if((object.optInt("code"))==201) {
                    String msg = object.optString("msg");
                    Toast.makeText(createAccount.this, msg, Toast.LENGTH_LONG).show();
                   // Toast.makeText(createAccount.this, "registration failed", Toast.LENGTH_LONG).show();
                }
                else if((object.optInt("code"))==202){
                    String msg = object.optString("msg");
                    Toast.makeText(createAccount.this, msg, Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(createAccount.this, "registration failed----", Toast.LENGTH_LONG).show();
            }
        }
    }
}

