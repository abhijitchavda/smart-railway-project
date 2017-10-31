package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by abhijit on 3/18/2017.
 */

public class Search_train_gen extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    AutoCompleteTextView source;
    String[] station_names;
    BackgroundTask backgroundTask;
    String hrs;
    String from;
    public final static String EXTRA_JSON_TRAIN="com.example.abhijit.helloandroid.jsontrain";
    public final static String EXTRA_STATION="com.example.abhijit.helloandroid.station";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_train_general);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar25);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        source=(AutoCompleteTextView) findViewById(R.id.gensourcestation);
        station_names=getResources().getStringArray(R.array.station_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,station_names);
        source.setAdapter(adapter);
        source.setThreshold(1);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),GenTicketEntryType.class);
            startActivity(intent);

            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        int select_id=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(select_id);
        String src1=source.getText().toString();
        from=src1;
        String src=src1.substring(src1.lastIndexOf("-") + 1);
        if(radioButton.getText().toString().equals("2hrs"))
        {
            hrs="2";
        }
        else if (radioButton.getText().toString().equals("4hrs"))
        {
            hrs="4";
        }
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Search_train_gen.this);
        if(src.length()>0)
        {
            backgroundTask.execute(src,hrs);
        }
        else
            Toast.makeText(Search_train_gen.this,"please fill in source station",Toast.LENGTH_LONG).show();

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

            String searchtrgen_url = BASE_URL+"searchtraingen4or2.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String src = params[0];
            String hrss= params[1];
            try {
                URL url = new URL(searchtrgen_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("station", "UTF-8") + "=" + URLEncoder.encode(src, "UTF-8") + "&"
                        + URLEncoder.encode("hrs", "UTF-8") + "=" + URLEncoder.encode(hrss, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                //StringBuffer buffer = new StringBuffer();
                while ((searchtrgen_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(searchtrgen_url + "\n");
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


                    Intent intent = new Intent(getApplicationContext(),Listview_gen_trains.class);
                    intent.putExtra(EXTRA_JSON_TRAIN,response);
                    intent.putExtra(EXTRA_STATION,from);
                    startActivity(intent);


                }
                else if ((object.optInt("response_code")) == 204) {
                    Toast.makeText(Search_train_gen.this, "No trains available", Toast.LENGTH_LONG).show();
                }
                else if ((object.optInt("response_code")) == 403) {
                    Toast.makeText(Search_train_gen.this, "NO HITS LEFT!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Search_train_gen.this, "Error:server side", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Search_train_gen.this, "null value return from api", Toast.LENGTH_LONG).show();
            }
        }
    }

    }



