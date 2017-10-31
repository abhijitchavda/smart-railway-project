package com.example.abhijit.helloandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

/**
 * Created by abhijit on 3/7/2017.
 */

public class ShowTicket extends AppCompatActivity {
    TextView trainno, src, des, comp, date, type_ticket;
    String email = Homepage.email;
    BackgroundTask backgroundTask;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_ticket);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        trainno = (TextView) findViewById(R.id.trainnumid);
        src = (TextView) findViewById(R.id.srcname);
        des = (TextView) findViewById(R.id.desname);
        comp = (TextView) findViewById(R.id.compartmentname);
        date = (TextView) findViewById(R.id.doj);
        type_ticket = (TextView) findViewById(R.id.type_ticket);
        if (backgroundTask != null) {
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(ShowTicket.this);
        type = "show";
        backgroundTask.execute(email, type);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);

            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void delete(View view) {

        if (backgroundTask != null) {
            backgroundTask = null;


        }
        ShareLatLong.count = 0;
        backgroundTask = new BackgroundTask(ShowTicket.this);
        type = "delete";
        backgroundTask.execute(email, type);

    }


    public class BackgroundTask extends AsyncTask<String, Void, String> {
        Context ctx;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            String pnr_url = BASE_URL + "showticket.php";
            String email = params[0];
            String type = params[1];
            try {
                URL url = new URL(pnr_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
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

                    trainno.setText(object.optString("train_no"));
                    date.setText(object.optString("date"));
                    src.setText(object.optString("src"));
                    des.setText(object.optString("des"));
                    comp.setText(object.optString("compartment"));
                    type_ticket.setText(object.optString("type"));

                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(ShowTicket.this, msg, Toast.LENGTH_LONG).show();

                } else if ((object.optInt("code")) == 400) {
                    String msg = object.optString("msg");
                    Toast.makeText(ShowTicket.this, msg, Toast.LENGTH_LONG).show();
                    ShareLatLong.count = 0;
                    if (ShareLatLong.flaglocget()) {
                        if (ActivityCompat.checkSelfPermission(ShowTicket.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowTicket.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        ShareLatLong.locationManager.removeUpdates(ShareLatLong.locationListener);
                    }
                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    startActivity(intent);
                }
                else if ((object.optInt("code")) == 401) {
                    String msg = object.optString("msg");
                    Toast.makeText(ShowTicket.this,msg, Toast.LENGTH_LONG).show();

                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(ShowTicket.this, "-------failed----", Toast.LENGTH_LONG).show();
            }
        }
    }
}
