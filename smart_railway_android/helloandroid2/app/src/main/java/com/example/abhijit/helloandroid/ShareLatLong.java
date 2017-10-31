package com.example.abhijit.helloandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants;

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

/**
 * Created by owner on 4/3/2017.
 */

public class ShareLatLong extends AppCompatActivity {
    public static int count = 0;
    Button share;
    TextView contact_name, contat_no;
    String cont_name, cont_no, lati, longi;
    static String email = Homepage.email;
    double lat, lon;
    public static LocationManager locationManager;
    public static LocationListener locationListener;
    BackgroundTask backgroundTask;
    public static Boolean flagloc = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_location_lat_lon);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar17);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        share = (Button) findViewById(R.id.sharelocationbtn);
        contact_name = (TextView) findViewById(R.id.contactnameid);
        contat_no = (TextView) findViewById(R.id.contactnumberid);
        cont_name = getIntent().getStringExtra(ShareLocationContact.EXTRA_CONTACT_NAME);
        cont_no = getIntent().getStringExtra(ShareLocationContact.EXTRA_CONTACT_NUMBER);
        contact_name.setText(cont_name);
        contat_no.setText(cont_no);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                lati = Double.toString(lat);
                longi = Double.toString(lon);
                if (backgroundTask != null) {
                    backgroundTask = null;


                }
                backgroundTask = new BackgroundTask(ShareLatLong.this);
                backgroundTask.execute(email, lati, longi, cont_no);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };

        configureButton();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                configureButton();
                break;
            default:
                break;
        }
    }

    void configureButton() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                flagloc = true;
            }
        });
    }

    @NonNull
    public void removeLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
        locationListener=null;
    }
    public static Boolean flaglocget(){return flagloc;}
   public class BackgroundTask extends AsyncTask<String,Void,String>{
    Context ctx;
    BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String shareloc_url = Constants.SHARE_LOC_URL;
        String email = params[0];
        String lat = params[1];
        String long_=params[2];
        String phoneno=params[3];
        try {
            URL url = new URL(shareloc_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                    + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8")+ "&"
                    + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(long_, "UTF-8")+ "&"
                    + URLEncoder.encode("phone_no", "UTF-8") + "=" + URLEncoder.encode(phoneno, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((shareloc_url = bufferedReader.readLine()) != null) {
                stringBuilder.append(shareloc_url + "\n");
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
        //super.onPostExecute(response);
        try {
           // if (TextUtils.isEmpty(response))
             //   return;

            JSONObject object = new JSONObject(response);
            if ((object.optInt("code")) == 200) {
                String msg = object.optString("msg");
                count=count+1;
                if(count==1)
                {
                    Toast.makeText(ShareLatLong.this,"your location is sent", Toast.LENGTH_LONG).show();
                    Toast.makeText(ShareLatLong.this, msg, Toast.LENGTH_LONG).show();

                }
              /*  Intent intent = new Intent(getApplicationContext(),Homepage.class);
                //intent.putExtra(EXTRA_EMAIL1, email);
                startActivity(intent);*/
                finish();
            }
            else if ((object.optInt("code")) == 201) {
                //  Toast.makeText(MainActivity.this, "IN FALSE", Toast.LENGTH_LONG).show();

                    String msg = object.optString("msg");
                    removeLocation();
                    Toast.makeText(ShareLatLong.this, msg, Toast.LENGTH_LONG).show();

            }
            else if ((object.optInt("code")) == 202) {
                //  Toast.makeText(MainActivity.this, "IN FALSE", Toast.LENGTH_LONG).show();

                    String msg = object.optString("msg");
                    removeLocation();
                    Toast.makeText(ShareLatLong.this, msg, Toast.LENGTH_LONG).show();
                }


            // else if((object.optInt("code")) == 201 && object.optString("msg") == "wrong email") {
            //  String msg = object.getString("msg");
            // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();}


        }
        catch(JSONException e){
            e.printStackTrace();
            removeLocation();
            Toast.makeText(ShareLatLong.this, " --------failed----", Toast.LENGTH_LONG).show();
        }
    }
}
}
