package com.example.abhijit.helloandroid;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by abhijit on 2/22/2017.
 */

public class Homepage extends AppCompatActivity
{
    public static boolean setlatilongi;
    public static int countlocation=0;
    public static String lati, longi;
    double lat, lon;
    public LocationManager locationManager;
    public LocationListener locationListener;
    private static final String TAG = "Homepage";
    public final static String EXTRA_EMAIL2="com.example.abhijit.helloandroid.emailin2";
    public static String email;
    TextView demo;
    BackgroundTask backgroundTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        email = MainActivity.email11; //getIntent().getStringExtra(MainActivity.EXTRA_EMAIL1);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                lati = Double.toString(lat);
                longi = Double.toString(lon);
                setlatilongi=true;
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                            , 10);
                }
                return;
            }
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(countlocation==0) {
            locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
        }
        countlocation=countlocation+1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    return;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }
//------------------------------menu list--------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_contactus)
        {
            Intent intent_contactus=new Intent(Homepage.this,Contactus.class);
            startActivity(intent_contactus);
            return true;
        }

        if(item.getItemId()==R.id.action_about_us)
        {
            Intent intent_aboutus=new Intent(Homepage.this,Aboutus.class);
            startActivity(intent_aboutus);
            return true;

        }
        if(item.getItemId()==R.id.action_logout)
        {

            Intent intent_logout=new Intent(Homepage.this,LogOut.class);
            startActivity(intent_logout);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
///-----------------------------------------------------------------------------------------
    public void searchtrain(View view){
        Intent intent = new Intent(getApplicationContext(),SearchTrain.class);
        startActivity(intent);

    }
public void share(View view)
{
    if(ShareLatLong.count==0) {
        Intent intent = new Intent(getApplicationContext(), ShareLocationContact.class);
        startActivity(intent);
    }
    else
    {
        Toast.makeText(Homepage.this, "your location is already sent.", Toast.LENGTH_LONG).show();
    }
}
public void cancelres(View view)
{
    Intent intent = new Intent(getApplicationContext(),CancelRes.class);
    startActivity(intent);
}
    public void beginjourney(View view){
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Homepage.this);
        backgroundTask.execute(email);


    }

    public void profile(View view){
        Intent intent = new Intent(getApplicationContext(),Profile.class);
        intent.putExtra(EXTRA_EMAIL2, email);
        startActivity(intent);
    }

    public void emergency(View view)
    {
        Intent intent = new Intent(getApplicationContext(),emergency.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
            if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
                Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
                Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
                Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
            } else if (!MyApplication.isBeaconNotificationsEnabled())
                 {
               if(Profile.notifications()) {
                    Log.d(TAG, "Enabling beacon notifications");
                    MyApplication.enableBeaconNotifications(Homepage.this);
                }
            }
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

            String pnr_url = BASE_URL+"checkticketstored.php";
            String email = params[0];
            try {
                URL url = new URL(pnr_url);
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

                    Intent intent = new Intent(getApplicationContext(),BeginJourney.class);
                    intent.putExtra(EXTRA_EMAIL2, email);
                    startActivity(intent);

                } else if ((object.optInt("code")) == 201) {
                    Intent intent = new Intent(getApplicationContext(), ShowTicket.class);
                    startActivity(intent);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(Homepage.this, "failed----", Toast.LENGTH_LONG).show();
            }
        }
    }

}
