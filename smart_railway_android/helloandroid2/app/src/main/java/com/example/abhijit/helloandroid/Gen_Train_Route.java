package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by abhijit on 3/19/2017.
 */

public class Gen_Train_Route extends AppCompatActivity {
    String train_num;
    String train_no;
    String src_station;
    BackgroundTask backgroundTask;
    public final static String EXTRA_JSON_RESPONSE="com.example.abhijit.helloandroid.jsonresponseroute";
    public final static String EXTRA_TRAIN_NUMBER_1="com.example.abhijit.helloandroid.trainnumberpass1";
    public final static String EXTRA_FROM_STATION_1="com.example.abhijit.helloandroid.fromstation1";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_train_route_nothing);
        train_num = getIntent().getStringExtra(Listview_gen_trains.EXTRA_TRAIN_NUMBER);
        src_station=getIntent().getStringExtra(Listview_gen_trains.EXTRA_FROM_STATION);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Gen_Train_Route.this);
        backgroundTask.execute(train_num);
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

            String searchtrgen_url = BASE_URL+"gentrainroute.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String train_num1 = params[0];
            train_num=train_num1.substring(train_num1.lastIndexOf(":") + 1);
            train_no=train_num;
            try {
                URL url = new URL(searchtrgen_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("train_no", "UTF-8") + "=" + URLEncoder.encode(train_num, "UTF-8");
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


                    Intent intent = new Intent(getApplicationContext(),Listview_gen_train_route.class);
                    intent.putExtra(EXTRA_JSON_RESPONSE,response);
                    intent.putExtra(EXTRA_FROM_STATION_1,src_station);
                    intent.putExtra(EXTRA_TRAIN_NUMBER_1,train_no);
                    startActivity(intent);


                }
                else if ((object.optInt("response_code")) == 204) {
                    Toast.makeText(Gen_Train_Route.this, "No Routes available", Toast.LENGTH_LONG).show();
                }
                else if ((object.optInt("response_code")) == 403) {
                    Toast.makeText(Gen_Train_Route.this, "NO HITS LEFT!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Gen_Train_Route.this, "Error:server side", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Gen_Train_Route.this, "null value return from api", Toast.LENGTH_LONG).show();
            }
        }
    }

}
