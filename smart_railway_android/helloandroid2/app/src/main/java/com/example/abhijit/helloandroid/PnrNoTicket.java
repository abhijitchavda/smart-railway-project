package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
 * Created by abhijit on 3/7/2017.
 */

public class PnrNoTicket extends AppCompatActivity {
    String email= Homepage.email;
    EditText pnr_no;
    BackgroundTask backgroundTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pnr_no_ticket);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar8);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        pnr_no= (EditText) findViewById(R.id.pnrnotake);

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

    public void savepnrno(View view){
 String pnrno= pnr_no.getText().toString();
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(PnrNoTicket.this);
        if(pnrno.length()!=10)
        {
            Toast.makeText(PnrNoTicket.this, "enter valid 10 digit pnr no.", Toast.LENGTH_LONG).show();
        }
        else
        {
            backgroundTask.execute(email,pnrno);
        }

    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        //  JSONObject object;
        // String method;
        // AlertDialog alertDialog;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            // alertDialog = new AlertDialog.Builder(ctx).create();
            // alertDialog.setTitle("account status");

        }

        @Override
        protected String doInBackground(String... params) {

            String pnr_url = BASE_URL+"pnrresticket.php";
            String email = params[0];
            String pnrno= params[1];
            try {
                URL url = new URL(pnr_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("pnr", "UTF-8") + "=" + URLEncoder.encode(pnrno, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((pnr_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(pnr_url + "\n");
                }
                String result = stringBuilder.toString();

                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            try {


                JSONObject object = new JSONObject(response);
                if ((object.optInt("code")) == 200) {

                    Toast.makeText(PnrNoTicket.this, "ticket stored", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), ShowTicket.class);
                    startActivity(intent);
                } else if ((object.optInt("code")) == 202) {
                    String msg = object.optString("msg");
                    Toast.makeText(PnrNoTicket.this,msg, Toast.LENGTH_LONG).show();

                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(PnrNoTicket.this,msg, Toast.LENGTH_LONG).show();

                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(PnrNoTicket.this, "null value return from api", Toast.LENGTH_LONG).show();
            }
        }
    }
}
