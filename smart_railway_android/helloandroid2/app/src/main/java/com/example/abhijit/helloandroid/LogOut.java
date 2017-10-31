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
 * Created by abhijit on 3/14/2017.
 */

public class LogOut extends AppCompatActivity {
    String email=Homepage.email;
    BackgroundTask backgroundTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(LogOut.this);
        backgroundTask.execute(email);

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

            String logout_url = BASE_URL+"logout.php";
            String email = params[0];
            try {
                URL url = new URL(logout_url);
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
                while ((logout_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(logout_url + "\n");
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

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                } else if ((object.optInt("code")) == 201) {
                    Toast.makeText(LogOut.this, "failed:server side", Toast.LENGTH_LONG).show();
                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(LogOut.this, "failed----", Toast.LENGTH_LONG).show();
            }
        }
    }
}
