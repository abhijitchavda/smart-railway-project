package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.SystemRequirementsChecker;

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
 * Created by abhijit on 3/8/2017.
 */

public class emergency extends AppCompatActivity {
    private static final String TAG = "emergency";
    ImageButton medic,police;
    TextView medictxt,policetxt;
    BackgroundTask backgroundTask;
    String beaconid;
    String email= Homepage.email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_call);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar6);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        medic=(ImageButton)findViewById(R.id.medicid);
        police=(ImageButton)findViewById(R.id.policeid);
        medictxt=(TextView)findViewById(R.id.medictxt);
        policetxt=(TextView)findViewById(R.id.policetxt);



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

    public void medic(View view){
if(BeaconNotificationsManager.locationon()) {
        Beacon beacon = BeaconNotificationsManager.getEmergencyBeacon();
        beaconid = String.valueOf(beacon.getMajor()) ;
        String type = "medic";
        if (backgroundTask != null) {
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(emergency.this);

        backgroundTask.execute(email, beaconid, type);
        }
        else
        {
            Toast.makeText(emergency.this,"get under a beacon range", Toast.LENGTH_LONG).show();
        }
    }
    public void police(View view){
        if(BeaconNotificationsManager.locationon()) {
            Beacon beacon = BeaconNotificationsManager.getEmergencyBeacon();
            beaconid = String.valueOf(beacon.getMajor()) ;
            String type = "police";
            if (backgroundTask != null) {
                backgroundTask = null;


            }
            backgroundTask = new BackgroundTask(emergency.this);

            backgroundTask.execute(email, beaconid, type);
        }
        else
        {
            Toast.makeText(emergency.this,"get under a beacon range", Toast.LENGTH_LONG).show();
        }
    }
    public class BackgroundTask extends AsyncTask<String, Void, String> {
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

            String login_url = BASE_URL+"emergency.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String email = params[0];
            String beaconid = params[1];
            String type = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("beaconid", "UTF-8") + "=" + URLEncoder.encode(beaconid, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((login_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(login_url + "\n");
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
                    String msg = object.optString("msg");
                    Toast.makeText(emergency.this, msg, Toast.LENGTH_LONG).show();
                    if(object.optString("type").equals("medic"))
                    {
                        medic.setBackgroundColor(Color.GREEN);
                        medictxt.setText("medical attention is on its way");
                    }
                    else if(object.optString("type").equals("police")){
                        police.setBackgroundColor(Color.GREEN);
                        policetxt.setText("police attention is on its way");
                    }
                }
                else if((object.optInt("code")) == 202){
                    String msg = object.optString("msg");
                    Toast.makeText(emergency.this, msg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
