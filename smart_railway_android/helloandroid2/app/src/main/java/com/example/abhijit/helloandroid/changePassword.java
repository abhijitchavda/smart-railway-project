package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 2/5/2017.
 */
public class changePassword extends AppCompatActivity{

databaseHelper mydb;
    createAccount myacc;
    Button save;
    EditText old,newp,conf;
    String email;
    BackgroundTask backgroundTask;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar4);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //mydb= new databaseHelper(this);
        myacc= new createAccount();
        save=(Button) findViewById(R.id.changepassbid);
        old=(EditText) findViewById(R.id.changeoldid);
        newp=(EditText) findViewById(R.id.changenewid);
        conf=(EditText) findViewById(R.id.changeconfid);
        email = Homepage.email;
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
    public void change(View view)
    {
        String oldpass=old.getText().toString();
        String newpass=newp.getText().toString();
        String confpass= conf.getText().toString();
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(changePassword.this);
        if(oldpass.length() > 0 && newpass.length() > 0 && confpass.length() > 0)
        {
          // String pass = mydb.login(email);
           // if(pass.equals(oldpass))
            //{
                if(confpass.equals(newpass))
                {
                    if(myacc.validpass(newpass))
                    {
                        backgroundTask.execute(email,newpass,oldpass);
                        //if(mydb.passchange(email,newpass))
                        //{
                          //  Toast.makeText(changePassword.this,"password changed",Toast.LENGTH_LONG).show();
                           // old.setText("");
                            //newp.setText("");
                            //conf.setText("");
                        //}
                    }
                    else
                    {
                        Toast.makeText(changePassword.this,"password must have minimum 8 characters with 1 special character, 1 capital alphabet and 1 digit.",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(changePassword.this,"password and confirm password dont match",Toast.LENGTH_LONG).show();
            //}
            //else
              //  Toast.makeText(changePassword.this,"invalid old password",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(changePassword.this,"All fields are  required.pls fill in.",Toast.LENGTH_LONG).show();
    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        //  JSONObject object;
        String method;
       // AlertDialog alertDialog;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
           super.onPreExecute();
            // alertDialog = new AlertDialog.Builder(ctx).create();
            //alertDialog.setTitle("account status");

        }


    @Override
    protected String doInBackground(String... params) {
        try {
            String changepass_url = BASE_URL+"passchange.php";
            String email = params[0];
            String newpassword = params[1];
            String oldpassword = params[2];
            URL url = new URL(changepass_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                    + URLEncoder.encode("newpassword", "UTF-8") + "=" + URLEncoder.encode(newpassword, "UTF-8")+ "&"
                    + URLEncoder.encode("oldpassword", "UTF-8") + "=" + URLEncoder.encode(oldpassword, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((changepass_url = bufferedReader.readLine()) != null) {
                stringBuilder.append(changepass_url + "\n");
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
                Toast.makeText(changePassword.this, "password changed", Toast.LENGTH_LONG).show();

               // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               // startActivity(intent);
            } else if((object.optInt("code"))==201) {
                if ((object.optString("msg")).equals("wrong old password")) {
                    String msg = object.optString("msg");
                    Toast.makeText(changePassword.this, msg, Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(changePassword.this, "failed", Toast.LENGTH_LONG).show();

                else if ((object.optString("msg")).equals("not loged in")) {
                    String msg = object.optString("msg");
                    Toast.makeText(changePassword.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            else if ((object.optInt("code"))==202){
                String msg = object.optString("msg");
                Toast.makeText(changePassword.this, msg, Toast.LENGTH_LONG).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(changePassword.this, "password change failed : try again...", Toast.LENGTH_LONG).show();
        }
    }
}
}

