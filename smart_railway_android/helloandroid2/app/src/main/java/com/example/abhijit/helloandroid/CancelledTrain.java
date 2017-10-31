package com.example.abhijit.helloandroid;

import android.app.Activity;
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
 * Created by abhijit on 4/3/2017.
 */

public class CancelledTrain extends AppCompatActivity {
    BackgroundTask backgroundTask;
    String email= Homepage.email;
    public final static String EXTRA_CANCELLEDTRAIN="com.example.abhijit.helloandroid.cancelledtrain";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelled_train_nothing);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(CancelledTrain.this);
        backgroundTask.execute(email);

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

            String canceltr_url = BASE_URL+"cancelledtrain.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String email = params[0];
            try {
                URL url = new URL(canceltr_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                //StringBuffer buffer = new StringBuffer();
                while ((canceltr_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(canceltr_url + "\n");
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

                    Intent intent = new Intent(getApplicationContext(),Listview_Canceltrain.class);
                    intent.putExtra(EXTRA_CANCELLEDTRAIN, response);
                    startActivity(intent);


                }
                else if ((object.optInt("response_code")) == 204) {
                    Toast.makeText(CancelledTrain.this, "No trains available", Toast.LENGTH_LONG).show();
                }
                else if ((object.optInt("response_code")) == 403) {
                    Toast.makeText(CancelledTrain.this, "NO HITS LEFT!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CancelledTrain.this, "Error:server side", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(CancelledTrain.this, "null value return from api", Toast.LENGTH_LONG).show();
            }
        }
    }

}
