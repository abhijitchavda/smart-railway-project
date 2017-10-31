package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
 * Created by abhijit on 2/27/2017.
 */

public class ReservationTicket extends AppCompatActivity {
    String email;
    String[] station_names,comp_names;
    AutoCompleteTextView source,destination,comp;
    EditText date_,train_no;
    BackgroundTask backgroundTask;
    String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_ticket);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar10);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if(getIntent().hasExtra(GenTicketEntryType.EXTRA_TYPE))
        {
            type=getIntent().getStringExtra(GenTicketEntryType.EXTRA_TYPE);
        }
        if(getIntent().hasExtra(ResTicketEntryType.EXTRA_TYPE))
        {
            type=getIntent().getStringExtra(ResTicketEntryType.EXTRA_TYPE);
        }
        email=Homepage.email;
        source=(AutoCompleteTextView) findViewById(R.id.sourceticket);
        destination=(AutoCompleteTextView) findViewById(R.id.destinationticket);
        comp=(AutoCompleteTextView) findViewById(R.id.compartmentid);
        station_names=getResources().getStringArray(R.array.station_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,station_names);
        source.setAdapter(adapter);
        source.setThreshold(1);
        destination.setAdapter(adapter);
        destination.setThreshold(1);
        comp_names=getResources().getStringArray(R.array.comp_names);
        ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,comp_names);
        comp.setAdapter(adapter_);
        comp.setThreshold(1);
        date_=(EditText)findViewById(R.id.ticketdateid);
        train_no=(EditText) findViewById(R.id.trainnoid);
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

    public void storeresticket(View view)
    {
        String date=date_.getText().toString();
        String trainno=train_no.getText().toString();
        String src=source.getText().toString();
        String des=destination.getText().toString();
        String srccode=src.substring(src.lastIndexOf("-") + 1);
        String descode=des.substring(des.lastIndexOf("-")+1);
        String compartment=comp.getText().toString();

        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(ReservationTicket.this);


        if(date.length()>0 && trainno.length()>0 && src.length()>0 && des.length()>0)
        {
            backgroundTask.execute(email,date,trainno,src,srccode,des,descode,compartment,type);
        }
        else
            Toast.makeText(ReservationTicket.this,"fill in all details(compartment is optional)",Toast.LENGTH_LONG).show();

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

            String restic_url = BASE_URL+"reservationticket.php";
            String email = params[0];
            String date= params[1];
            String trainno= params[2];
            String src= params[3];
            String srccode= params[4];
            String des= params[5];
            String descode=params[6];
            String compartment= params[7];
            String type=params[8];
            try {
                URL url = new URL(restic_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("trainno", "UTF-8") + "=" + URLEncoder.encode(trainno, "UTF-8") + "&"
                        + URLEncoder.encode("src", "UTF-8") + "=" + URLEncoder.encode(src, "UTF-8") + "&"
                        + URLEncoder.encode("srccode", "UTF-8") + "=" + URLEncoder.encode(srccode, "UTF-8") + "&"
                        + URLEncoder.encode("des", "UTF-8") + "=" + URLEncoder.encode(des, "UTF-8") + "&"
                        + URLEncoder.encode("descode", "UTF-8") + "=" + URLEncoder.encode(descode, "UTF-8") + "&"
                        + URLEncoder.encode("compartment", "UTF-8") + "=" + URLEncoder.encode(compartment, "UTF-8") + "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((restic_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(restic_url + "\n");
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

                    Toast.makeText(ReservationTicket.this, "details saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ShowTicket.class);
                    startActivity(intent);
                } else if ((object.optInt("code")) == 202) {
                    String msg = object.optString("msg");
                    Toast.makeText(ReservationTicket.this, msg, Toast.LENGTH_LONG).show();

                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(ReservationTicket.this, msg, Toast.LENGTH_LONG).show();

                }
            }
                catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(ReservationTicket.this, "failed----", Toast.LENGTH_LONG).show();
                }
            }
        }
}
