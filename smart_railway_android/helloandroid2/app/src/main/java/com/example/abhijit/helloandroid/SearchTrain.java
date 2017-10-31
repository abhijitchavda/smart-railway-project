package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 2/22/2017.
 */

public class SearchTrain extends AppCompatActivity{

    AutoCompleteTextView source,destination;
    Button strain;
    String[] station_names;
    EditText date1;
    BackgroundTask backgroundTask;
    public final static String EXTRA_EMAIL3="com.example.abhijit.helloandroid.emailin3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_train);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar11);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        date1=(EditText)findViewById(R.id.dateid);
        source=(AutoCompleteTextView) findViewById(R.id.sourcespinner);
        destination=(AutoCompleteTextView) findViewById(R.id.destinationspinner);
        station_names=getResources().getStringArray(R.array.station_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,station_names);
        source.setAdapter(adapter);
        source.setThreshold(1);

        destination.setAdapter(adapter);
        destination.setThreshold(1);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),Homepage.class);
            startActivity(intent);

            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchtr(View view)
    {
        String src1=source.getText().toString();
        String des1=destination.getText().toString();
        String date=date1.getText().toString();

        String src=src1.substring(src1.lastIndexOf("-") + 1);
        String des=des1.substring(des1.lastIndexOf("-")+1);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(SearchTrain.this);
        if(src.length()>0 && des.length()>0 && date.length()>0)
        {
           backgroundTask.execute(date,src,des);
        }
        else
            Toast.makeText(SearchTrain.this,"please fill in all the details",Toast.LENGTH_LONG).show();

    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        //  JSONObject object;
        //String method;
        //AlertDialog alertDialog;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
          //  alertDialog = new AlertDialog.Builder(ctx).create();
           // alertDialog.setTitle("account status");

        }

        @Override
        protected String doInBackground(String... params) {

            String searchtr_url = BASE_URL+"findtrain.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String date = params[0];
            String src = params[1];
            String des= params[2];
            try {
                URL url = new URL(searchtr_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode(src, "UTF-8") + "&"
                        + URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode(des, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                //StringBuffer buffer = new StringBuffer();
                while ((searchtr_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(searchtr_url + "\n");
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

        try{

                JSONObject object = new JSONObject(response);
                if ((object.optInt("response_code")) == 200) {
                   // Toast.makeText(SearchTrain.this, "trains available", Toast.LENGTH_LONG).show();
                    //String email = object.optString("email");

                    Intent intent = new Intent(getApplicationContext(),Listview_findtrain.class);
                    intent.putExtra(EXTRA_EMAIL3, response);
                    startActivity(intent);


                }
                else if ((object.optInt("response_code")) == 204) {
                    Toast.makeText(SearchTrain.this, "No trains available", Toast.LENGTH_LONG).show();
                }
                else if ((object.optInt("response_code")) == 403) {
                    Toast.makeText(SearchTrain.this, "NO HITS LEFT!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SearchTrain.this, "Error:server side", Toast.LENGTH_LONG).show();
                }
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(SearchTrain.this, "null value return from api", Toast.LENGTH_LONG).show();
        }
        }
       }

}
