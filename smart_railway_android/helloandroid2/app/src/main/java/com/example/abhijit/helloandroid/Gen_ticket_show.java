package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import java.util.Date;

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 3/20/2017.
 */

public class Gen_ticket_show extends AppCompatActivity{
    String train_num,src_station,des_station,desdemo;
    TextView trainno,src,des,comp,date,type_ticket;
    String email= Homepage.email;
    BackgroundTask backgroundTask;
    String srccode,descode,date_;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_ticket_gen);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar22);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        trainno=(TextView)findViewById(R.id.trainnumid);
        src=(TextView)findViewById(R.id.srcname);
        des=(TextView)findViewById(R.id.desname);
        comp=(TextView)findViewById(R.id.compartmentname);
        date=(TextView)findViewById(R.id.doj);
        type_ticket=(TextView)findViewById(R.id.type_ticket);
        train_num = getIntent().getStringExtra(Listview_gen_train_route.EXTRA_TRAIN_NUMBER_2);
        src_station=getIntent().getStringExtra(Listview_gen_train_route.EXTRA_FROM_STATION_2);
        desdemo=getIntent().getStringExtra(Listview_gen_train_route.EXTRA_TO_STATION_2);
        des_station=desdemo.substring(desdemo.lastIndexOf(":") + 1);
        srccode=src_station.substring(src_station.lastIndexOf("-") + 1);
        descode=des_station.substring(des_station.lastIndexOf("-")+1);
        date_ = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        trainno.setText(train_num);
        src.setText(src_station);
        des.setText(des_station);
        comp.setText("GEN");
        type_ticket.setText("GENERAL");
        date.setText(date_);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),Search_train_gen.class);
            startActivity(intent);

           // finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

   public void addticket(View view)
   {
       if (backgroundTask != null){
           backgroundTask = null;


       }
       backgroundTask = new BackgroundTask(Gen_ticket_show.this);
       backgroundTask.execute(email,train_num,src_station,srccode,des_station,descode,"GEN","GENERAL",date_);
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

            String addticketgen_url = BASE_URL+"reservationticket.php";
            String email = params[0];
           String train_num = params[1];
            String src_station=params[2];
            String srccode=params[3];
            String des_station=params[4];
            String descode=params[5];
            String comp=params[6];
            String type=params[7];
            String date=params[8];
            try {
                URL url = new URL(addticketgen_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("trainno", "UTF-8") + "=" + URLEncoder.encode(train_num, "UTF-8")+ "&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&"
                        + URLEncoder.encode("src", "UTF-8") + "=" + URLEncoder.encode(src_station, "UTF-8")+ "&"
                        + URLEncoder.encode("srccode", "UTF-8") + "=" + URLEncoder.encode(srccode, "UTF-8")+ "&"
                        + URLEncoder.encode("des", "UTF-8") + "=" + URLEncoder.encode(des_station, "UTF-8")+ "&"
                        + URLEncoder.encode("descode", "UTF-8") + "=" + URLEncoder.encode(descode, "UTF-8")+ "&"
                        + URLEncoder.encode("compartment", "UTF-8") + "=" + URLEncoder.encode(comp, "UTF-8")+ "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((addticketgen_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(addticketgen_url + "\n");
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
                    Toast.makeText(Gen_ticket_show.this,msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    startActivity(intent);

                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(Gen_ticket_show.this,msg, Toast.LENGTH_LONG).show();

                }
                else if ((object.optInt("code")) == 202) {
                    String msg = object.optString("msg");
                    Toast.makeText(Gen_ticket_show.this,msg, Toast.LENGTH_LONG).show();

                }
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(Gen_ticket_show.this, "-------failed----", Toast.LENGTH_LONG).show();
            }
        }
    }
}
