package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

public class Profile extends AppCompatActivity {
    TextView name,contactno,city;
    Button changepass;
    Switch mySwitch;
    String email= Homepage.email;
    private static boolean notify;
    BackgroundTask backgroundTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (backgroundTask != null){
            backgroundTask = null;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar13);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        name=(TextView)findViewById(R.id.name);
        contactno=(TextView)findViewById(R.id.contactno);
        city=(TextView)findViewById(R.id.city);
        changepass=(Button)findViewById(R.id.change_password);
        backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(new String[]{email});
        mySwitch = (Switch) findViewById(R.id.switch1);

        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    notify=true;
                    //Toast.makeText(Profile.this, "Notifications Enabled", Toast.LENGTH_LONG).show();
                    //switchStatus.setText("Switch is currently ON");
                }else{
                    notify=false;
                    //Toast.makeText(Profile.this, "Notifications Disabled", Toast.LENGTH_LONG).show();
                    //switchStatus.setText("Switch is currently OFF");
                }

            }
        });

        if(notify==false)
        {
            mySwitch.setChecked(false);
        }
        if(notify==true){
            mySwitch.setChecked(true);
        }

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

    public void changepassword (View view)
    {
        Intent intent = new Intent(getApplicationContext(),changePassword.class);
        startActivity(intent);
    }

    public static boolean notifications ()
    {

        return notify;
    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            String profile_url = BASE_URL+"profile.php";
            String email = params[0];

            try {
                URL url = new URL(profile_url);
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
                while ((profile_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(profile_url + "\n");
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

                    name.setText(object.optString("name"));
                    contactno.setText(object.optString("contact_no"));
                    city.setText(object.optString("city"));

                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(Profile.this,msg, Toast.LENGTH_LONG).show();

                }

            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(Profile.this, "failed----", Toast.LENGTH_LONG).show();
            }
        }
    }
}


