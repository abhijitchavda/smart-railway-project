package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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
import java.text.ParseException;
import java.util.Date;

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 3/2/2017.
 */

public class Notification extends AppCompatActivity {
    TextView platform_no,status,notify3,lateby,sch_arrival,est_arrival,notify1,plt,train_no,train_name,notify2,notify4;
    public static String response1;
   public static String platform="3";
    Date current;
    Date con_late;
    BackgroundTask backgroundTask;
    ImageView foodservice;
    String email=Homepage.email;
    long curr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        try {
            current= dateFormat.parse(dateFormat.format(cal.getTime()));
            curr=current.getTime();
            con_late= dateFormat.parse("00:10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        platform_no=(TextView) findViewById(R.id.platform_no);
        foodservice=(ImageView)findViewById(R.id.imageView4);
        status= (TextView)findViewById(R.id.status);
        lateby=(TextView)findViewById(R.id.lateby);
        plt=(TextView) findViewById(R.id.plt);
        sch_arrival=(TextView)findViewById(R.id.sch_arrival);
        est_arrival=(TextView)findViewById(R.id.est_arrival);
        notify1=(TextView)findViewById(R.id.notification1);
        notify2=(TextView)findViewById(R.id.notification2);
        notify3=(TextView)findViewById(R.id.notification3);
        notify4=(TextView)findViewById(R.id.notification4);
        train_no=(TextView)findViewById(R.id.train_no);
        train_name=(TextView)findViewById(R.id.train_name);
        response1= getIntent().getStringExtra(BeaconNotificationsManager.EXTRA_RESPONSE1);

        try {
            JSONObject jsonObject=new JSONObject(response1);
            //-----------------------gen add-----------
           /* if(jsonObject.optString("type").equals("gen_add"))
            {
                if(jsonObject.optInt("code")== 200) {
                    Toast.makeText(Notification.this, "added",Toast.LENGTH_LONG).show();
                }
                else if(jsonObject.optInt("code")== 202) {
                    Toast.makeText(Notification.this, "not added", Toast.LENGTH_LONG).show();
                }
            }
            //---------------------gen sub------------
            if(jsonObject.optString("type").equals("gen_sub"))
            {
                if(jsonObject.optInt("code")== 200) {
                    Toast.makeText(Notification.this, "removed",Toast.LENGTH_LONG).show();
                }
                else if(jsonObject.optInt("code")== 202) {
                    Toast.makeText(Notification.this, "not removed", Toast.LENGTH_LONG).show();
                }
                else if(jsonObject.optInt("code")== 203) {
                    Toast.makeText(Notification.this, "not listed first so not removed", Toast.LENGTH_LONG).show();
                }
            }*/
            //-----------------------on enterence or on platform or compartment-------------
            if(jsonObject.optString("type").equals("ON_ENTERENCE")  ||  jsonObject.optString("type").equals("ON_PLATFORM") || jsonObject.optString("type").equals("ON_COMPARTMENT")|| jsonObject.optString("type").equals("ON_TRAIN")) {
                if (jsonObject.optInt("code") == 201) {
                    if (jsonObject.optString("msg").equals("Train does not stop on this station")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        notify1.setText(" trains route : /n" + jsonObject.optString("train_route"));
                        platform_no.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }else if (jsonObject.optString("msg").equals("You are standing on wrong platform. please reach your platform no")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        platform_no.setText(platform);
                        notify1.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }
                    else if (jsonObject.optString("msg").equals("Train not running on this date")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        platform_no.setText("");
                        notify1.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }else if (jsonObject.optString("msg").equals("no response data from API")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        platform_no.setText("");
                        notify1.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }
                    else if (jsonObject.optString("msg").equals("hits finish")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        platform_no.setText("");
                        notify1.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }
                    else if (jsonObject.optString("msg").equals("no active saved tickets")) {
                        train_no.setText("");
                        train_name.setText("");
                        status.setText("ALERT : "+jsonObject.optString("msg"));
                        platform_no.setText("");
                        notify1.setText("");
                        lateby.setText("");
                        plt.setText("");
                        sch_arrival.setText("");
                        est_arrival.setText("");
                    }
                } else if (jsonObject.optInt("code") == 200) {
                    if (jsonObject.optString("type").equals("ON_ENTERENCE")) {
                        if (jsonObject.optString("ticket").equals("RESERVATION")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            notify1.setText("Reach platform no : " + jsonObject.optString("platform_no"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                            if(curr>=act && curr<=gone)
                            {
                                notify2.setText("Reach your compartment location, your train will be arriving in few minutes");
                            }
                            //notify1.setText(" trains route : \n"+jsonObject.optString("train_route")+jsonObject.optString("train_route")+jsonObject.optString("train_route"));
                        }
                        if (jsonObject.optString("ticket").equals("GENERAL")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            notify1.setText("Reach platform no : " + jsonObject.optString("platform_no"));
                            notify2.setText(" No. of people in GEN compartment currently: " + jsonObject.optString("gen_people"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                            if(curr>=act && curr<=gone)
                            {
                                notify3.setText("Reach your compartment location, your train will be arriving in few minutes");
                            }
                        }
                    }
                    if (jsonObject.optString("type").equals("ON_PLATFORM")) {
                        if (jsonObject.optString("ticket").equals("RESERVATION")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                            if(curr>=act && curr<=gone)
                            {
                                notify3.setText("Reach your compartment location, your train will be arriving in few minutes");
                            }
                            notify1.setText("Reached your platform");
                            //notify1.setText(" trains route : \n"+jsonObject.optString("train_route")+jsonObject.optString("train_route")+jsonObject.optString("train_route"));
                            if(jsonObject.optString("comp_location").equals("Y"))
                            {
                                if (jsonObject.optString("comp_direction").equals("LR")) {
                                    notify2.setText("Your compartment will be positioned on " + jsonObject.optString("comp_position") + "marker from RIGHT side of the platform");
                                }
                                if (jsonObject.optString("comp_direction").equals("RL")) {
                                    notify2.setText("Your compartment will be positioned on " + jsonObject.optString("comp_position") + "marker from LEFT side of the platform");
                                }
                            }
                            if(jsonObject.optString("comp_location").equals("N"))
                            {
                    notify2.setText(jsonObject.optString("msg"));
                            }
                        }
                        if (jsonObject.optString("ticket").equals("GENERAL")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            notify1.setText("Reached your platform");
                            notify2.setText(" No. of people in GEN compartment currently: " + jsonObject.optString("gen_people"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                            if(curr>=act && curr<=gone)
                            {
                                notify4.setText("Reach your compartment location, your train will be arriving in few minutes");
                            }
                            if(jsonObject.optString("comp_location").equals("Y"))
                            {
                                if (jsonObject.optString("comp_direction").equals("LR")) {
                                    notify3.setText("Your compartment will be positioned on " + jsonObject.optString("comp_position") + "marker from RIGHT side of the platform");
                                }
                                if (jsonObject.optString("comp_direction").equals("RL")) {
                                    notify3.setText("Your compartment will be positioned on " + jsonObject.optString("comp_position") + "marker from LEFT side of the platform");
                                }
                            }
                            if(jsonObject.optString("comp_location").equals("N"))
                            {
                                    notify3.setText(jsonObject.optString("msg"));
                            }

                        }
                    }

                    if (jsonObject.optString("type").equals("ON_TRAIN")) {
                        train_no.setText("Train No : " + jsonObject.optString("train_no"));
                        train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                        lateby.setText("running " + jsonObject.optString("late"));
                        status.setText(jsonObject.optString("current_position"));
                        plt.setText("Distance(km)");
                        platform_no.setText(jsonObject.optString("distance"));
                        sch_arrival.setText("scheduled reach at destination station : " + jsonObject.optString("sch_arr_time"));
                        est_arrival.setText("Estimated reach at destination station : " + jsonObject.optString("act_arr_time"));
                        notify1.setText("Train route\n"+jsonObject.optString("train_route"));
                        foodservice.setImageResource(R.drawable.food);
                        if(jsonObject.optString("flag").equals("Y")){
                            BeaconNotificationsManager notify= new BeaconNotificationsManager(Notification.this);
                            notify.showNotification("You are about "+jsonObject.optString("distance")+" away form your destination get ready!!!");
                        }
                        if(jsonObject.optString("distance").equals("0"))
                        {
                            BeaconNotificationsManager notify= new BeaconNotificationsManager(Notification.this);
                            notify.showNotification("you are less than 1KM away from your destination. We hope you had a great journey with us. Thank You");
                            if (backgroundTask != null){
                                backgroundTask = null;


                            }
                            backgroundTask = new BackgroundTask(Notification.this);
                            backgroundTask.execute(email);
                        }


                    }
                    if (jsonObject.optString("type").equals("ON_COMPARTMENT")) {
                        if (jsonObject.optString("ticket").equals("RESERVATION")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            notify1.setText(jsonObject.optString("msg"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                            //notify2.setText("your train will be arriving in few minutes");

                            if(curr>=act && curr<=gone)
                            {
                                notify2.setText("your train will be arriving in few minutes");
                            }
                            notify3.setText("Train Route : \n"+jsonObject.optString("train_route"));
                            //notify1.setText(" trains route : \n"+jsonObject.optString("train_route")+jsonObject.optString("train_route")+jsonObject.optString("train_route"));
                        }
                        if (jsonObject.optString("ticket").equals("GENERAL")) {
                            train_no.setText("Train No : " + jsonObject.optString("train_no"));
                            train_name.setText("Train Name : " + jsonObject.optString("train_name"));
                            status.setText(jsonObject.optString("current_position"));
                            platform_no.setText(jsonObject.optString("platform_no"));
                            platform = jsonObject.optString("platform_no");
                            status.setText("Current Status : " + jsonObject.optString("current_position"));
                            lateby.setText("running " + jsonObject.optString("late"));
                            sch_arrival.setText("scheduled arrival at your station : " + jsonObject.optString("sch_arr_time"));
                            est_arrival.setText("Estimated arrival at your station : " + jsonObject.optString("act_arr_time"));
                            notify1.setText(jsonObject.optString("msg"));
                            notify2.setText(" No. of people in GEN compartment currently: " + jsonObject.optString("gen_people"));
                            Date act_arr=dateFormat.parse(jsonObject.optString("act_arr_time"));
                            long gone=act_arr.getTime();
                            long act = act_arr.getTime()- con_late.getTime();
                           // notify3.setText(" your train will be arriving in few minutes");

                            if(curr>=act && curr<=gone)
                            {
                                notify3.setText(" your train will be arriving in few minutes");
                            }
                            notify4.setText("Train Route : \n"+jsonObject.optString("train_route"));
                        }
                    }


                } else if (jsonObject.optInt("code") == 501) {
                    status.setText(jsonObject.optString("msg"));
                } else if (jsonObject.optInt("code") == 202) {
                    status.setText(jsonObject.optString("msg"));
                }
            }
            //-------------------------------------
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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

            String onreach_url = BASE_URL+"onreachdestination.php";
            String email = params[0];
            try {
                URL url = new URL(onreach_url);
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
                while ((onreach_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(onreach_url + "\n");
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

                    //Toast.makeText(Notification.this, "Ticket Deleted", Toast.LENGTH_LONG).show();
                    String msg = object.optString("msg");
                    Toast.makeText(Notification.this, msg, Toast.LENGTH_LONG).show();

//                    Intent intent = new Intent(getApplicationContext(), Homepage.class);
  //                  startActivity(intent);
                }else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(Notification.this, msg, Toast.LENGTH_LONG).show();

                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(Notification.this, "destination failed----", Toast.LENGTH_LONG).show();
            }
        }
    }


}
