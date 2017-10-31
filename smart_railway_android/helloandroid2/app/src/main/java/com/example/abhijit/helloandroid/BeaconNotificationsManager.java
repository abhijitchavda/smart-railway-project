package com.example.abhijit.helloandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

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
import java.util.HashMap;
import java.util.List;

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 3/2/2017.
 */

public class BeaconNotificationsManager {
    private static final String TAG = "BeaconNotifications";
    public final static String EXTRA_RESPONSE1 = "com.example.abhijit.helloandroid.response1";
    private BeaconManager beaconManager;
    BackgroundTask backgroundTask;
    private List<Region> regionsToMonitor = new ArrayList<>();
    private HashMap<String, String> enterMessages = new HashMap<>();
    private HashMap<String, String> exitMessages = new HashMap<>();

    private Context context;
    private final int ON_ENTERENCE_MAJOR = 44979;
    private final int ON_ENRERENCE_MINOR = 35401;
    private final int ON_GENERAL_MAJOR = 44979;
    private final int ON_GENERAL_MINOR = 35401;
    private final int ON_PLATFORM_MAJOR=44979;//62314;//38813;
    private final int ON_PLATFORM_MINOR=35401;//63730;//34937;
    private final int ON_COMPARTMENT_MAJOR=44979;//62314;
    private final int ON_COMPARTMENT_MINOR=35401;//63730;
    private final int ON_TRAIN_MAJOR=44979;//44979;
    private final int ON_TRAIN_MINOR=35401;//35401;
    private int notificationID = 0;
    private final String PLATFORM_NO_3="3";
    private static Beacon emergencyBeacon;
    private static boolean locationon;

    public BeaconNotificationsManager(final Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                locationon = true;
                emergencyBeacon = list.get(0);

                if(Profile.notifications()) {
                    for (Beacon beacon : list) {
                        if (beacon.getMajor() == ON_GENERAL_MAJOR) {
                            if (beacon.getMinor() == ON_GENERAL_MINOR) {
                                String email = Homepage.email;
                                String type = "GEN_COUNT_ADD";
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);

                                //do something for this beacon
                            }
                        }
                        if (beacon.getMajor() == ON_ENTERENCE_MAJOR) {
                            if (beacon.getMinor() == ON_ENRERENCE_MINOR) {
                                String email = Homepage.email;
                                String type = "ENT_COUNT_ADD";
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);

                                //do something for this beacon
                            }
                        }
                        if (beacon.getMajor() == ON_PLATFORM_MAJOR) {
                            if (beacon.getMinor() == ON_PLATFORM_MINOR) {
                                String email = Homepage.email;
                                String type = "PLAT_COUNT_ADD";
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);

                                //do something for this beacon
                            }
                        }
                        if (beacon.getMajor() == ON_COMPARTMENT_MAJOR) {
                            if (beacon.getMinor() == ON_COMPARTMENT_MINOR) {
                                String email = Homepage.email;
                                String type = "COMP_COUNT_ADD";
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);

                                //do something for this beacon
                            }
                        }

                        if (beacon.getMajor() == ON_ENTERENCE_MAJOR) {
                            if (beacon.getMinor() == ON_ENRERENCE_MINOR) {
                                String email = Homepage.email;
                                String type = "ON_ENTERENCE";
                                if (backgroundTask != null) {
                                    backgroundTask = null;
                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);

                                //do something for this beacon
                            }
                        }

                        if (beacon.getMajor() == ON_PLATFORM_MAJOR) {
                            if (beacon.getMinor() == ON_PLATFORM_MINOR) {
                                String email = Homepage.email;
                                String type = "ON_PLATFORM";
                                String platformno = Notification.platform;
                                if (platformno.equals(PLATFORM_NO_3)) {
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);


                                }
                                else
                                {
                                    String response;
                                    JSONObject mJson = new JSONObject();
                                    try {
                                        mJson.put("code",201);
                                        mJson.put("type", "ON_PLATFORM");
                                        mJson.put("msg", "You are standing on wrong platform. please reach your platform no");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                   response=mJson.toString();
                                    Intent intent = new Intent(context,Notification.class);
                                    intent.putExtra(EXTRA_RESPONSE1, response);
                                    context.startActivity(intent);
                                }
                            }
                        }
                        if (beacon.getMajor() == ON_COMPARTMENT_MAJOR) {
                            if (beacon.getMinor() == ON_COMPARTMENT_MINOR) {
                                String email = Homepage.email;
                                String type = "ON_COMPARTMENT";
                                if (backgroundTask != null) {
                                    backgroundTask = null;


                                }
                                backgroundTask = new BackgroundTask(context);

                                backgroundTask.execute(email, type);


                            }
                        }

                        if (beacon.getMajor() == ON_TRAIN_MAJOR) {
                            if (beacon.getMinor() == ON_TRAIN_MINOR) {
                                if (Homepage.setlatilongi) {
                                    String email = Homepage.email;
                                    String type = "ON_TRAIN";
                                    String lat = Homepage.lati;
                                    String lon = Homepage.longi;
                                    if (backgroundTask != null) {
                                        backgroundTask = null;


                                    }
                                    backgroundTask = new BackgroundTask(context);

                                    backgroundTask.execute(email, type);

                                }

                            }
                        }

                    }

                }
            }

            @Override
            public void onExitedRegion(Region region) {

                if (region.getMajor() == ON_GENERAL_MAJOR) {
                    if (region.getMinor() == ON_GENERAL_MINOR) {
                        String email = Homepage.email;
                        String type = "GEN_COUNT_SUB";
                        if (backgroundTask != null) {
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(context);

                        backgroundTask.execute(email, type);

                        //do something for this beacon
                    }
                }

                if (region.getMajor() == ON_ENTERENCE_MAJOR) {
                    if (region.getMinor() == ON_ENRERENCE_MINOR) {
                        String email = Homepage.email;
                        String type = "ENT_COUNT_SUB";
                        if (backgroundTask != null) {
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(context);

                        backgroundTask.execute(email, type);

                        //do something for this beacon
                    }
                }

                if (region.getMajor() == ON_PLATFORM_MAJOR) {
                    if (region.getMinor() == ON_PLATFORM_MINOR) {
                        String email = Homepage.email;
                        String type = "PLAT_COUNT_SUB";
                        if (backgroundTask != null) {
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(context);

                        backgroundTask.execute(email, type);

                        //do something for this beacon
                    }
                }

                if (region.getMajor() == ON_COMPARTMENT_MAJOR) {
                    if (region.getMinor() == ON_COMPARTMENT_MINOR) {
                        String email = Homepage.email;
                        String type = "COMP_COUNT_SUB";
                        if (backgroundTask != null) {
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(context);

                        backgroundTask.execute(email, type);

                        //do something for this beacon
                    }
                }
            }
        });
    }

    public static Beacon getEmergencyBeacon() {
        return emergencyBeacon;
    }

    public static boolean locationon() {
        return locationon;
    }

    public void addNotification(BeaconID beaconID, String enterMessage, String exitMessage) {
        locationon = false;
        Region region = beaconID.toBeaconRegion();
        enterMessages.put(region.getIdentifier(), enterMessage);
        exitMessages.put(region.getIdentifier(), exitMessage);
        regionsToMonitor.add(region);
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                for (Region region : regionsToMonitor) {
                    beaconManager.startMonitoring(region);
                }
            }
        });
    }


    public void showNotification(String message) {

        //Intent intent= new Intent(context,com.example.abhijit.helloandroid.Homepage.class);
        //context.startActivity(intent);

        //Intent resultIntent = new Intent(context, Homepage.class);
        //PendingIntent resultPendingIntent = PendingIntent.getActivity(
          //      context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Destination Alarm")
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
            //    .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());

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

            String onenterence_url = BASE_URL + "livetrain.php";
            String gencount_url = BASE_URL + "gencount.php";
            String entcount_url = BASE_URL + "ent_count.php";
            String platcount_url = BASE_URL + "plat_count.php";
            String compcount_url = BASE_URL + "comp_count.php";
            String onplatform_url= BASE_URL+"onplatform.php";
            String oncomp_url=BASE_URL+"oncompartmentpos.php";
            String ontrain_url=BASE_URL+"ontrain.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String email = params[0];
            String type = params[1];

            if (type.equals("ON_ENTERENCE")) {
                try {
                    URL url = new URL(onenterence_url);
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
                    while ((onenterence_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(onenterence_url + "\n");
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
            }
            else if (type.equals("ON_PLATFORM")) {
                try {
                    URL url = new URL(onplatform_url);
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
                    while ((onplatform_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(onplatform_url + "\n");
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
            }else if (type.equals("ON_COMPARTMENT")) {
                try {
                    URL url = new URL(oncomp_url);
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
                    while ((oncomp_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(oncomp_url + "\n");
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
            }
            else if (type.equals("ON_TRAIN")) {
                try {
                    URL url = new URL(ontrain_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&"
                            + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(Homepage.lati, "UTF-8")+ "&"
                            + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(Homepage.longi, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((ontrain_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(ontrain_url + "\n");
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
            }
            else if (type.equals("GEN_COUNT_ADD")) {
                try {

                    URL url = new URL(gencount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("ADD", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((gencount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(gencount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (type.equals("GEN_COUNT_SUB")) {
                try {

                    URL url = new URL(gencount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("SUB", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((gencount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(gencount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //---------on plat,ent,comp add---//
            else if (type.equals("ENT_COUNT_ADD")) {
                try {

                    URL url = new URL(entcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("ADD", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((entcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(entcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (type.equals("PLAT_COUNT_ADD")) {
                try {

                    URL url = new URL(platcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("ADD", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((platcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(platcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (type.equals("COMP_COUNT_ADD")) {
                try {

                    URL url = new URL(compcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("ADD", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((compcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(compcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //---------on plat,ent,comp sub----//

            else if (type.equals("ENT_COUNT_SUB")) {
                try {

                    URL url = new URL(entcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("SUB", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((entcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(entcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (type.equals("PLAT_COUNT_SUB")) {
                try {

                    URL url = new URL(platcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("SUB", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((platcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(platcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (type.equals("COMP_COUNT_SUB")) {
                try {

                    URL url = new URL(compcount_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("SUB", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((compcount_url = bufferedReader.readLine()) != null) {
                        stringBuilder.append(compcount_url + "\n");
                    }
                    String result = stringBuilder.toString();

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return null;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(String response) {

            if(response!=null) {
                Intent intent = new Intent(ctx, Notification.class);
                intent.putExtra(EXTRA_RESPONSE1, response);
                ctx.startActivity(intent);
            }

        }

    }
}
