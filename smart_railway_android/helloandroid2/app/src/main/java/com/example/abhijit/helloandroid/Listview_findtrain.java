package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.internal.utils.L;
import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.TrainModel;

import org.json.JSONArray;
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
import java.util.List;



public class Listview_findtrain extends AppCompatActivity {

    String jsonres;
    ListView trainli;
    BackgroundTask backgroundTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_findtrain);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        trainli=(ListView)findViewById(R.id.trainli);
        jsonres = getIntent().getStringExtra(SearchTrain.EXTRA_EMAIL3);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Listview_findtrain.this);
        backgroundTask.execute(jsonres);

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
    public class BackgroundTask extends AsyncTask<String,Void,List<TrainModel>> {
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
        protected List<TrainModel> doInBackground(String... params) {

            try {
            String finalJson= params[0];

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("train");

                List<TrainModel> trainModelList= new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TrainModel trainModel= new TrainModel();
                    trainModel.setNo(finalObject.optInt("no"));
                    trainModel.setName(finalObject.optString("name"));
                    trainModel.setNumber(finalObject.optInt("number"));
                    trainModel.setSrc_departure_time(finalObject.optString("src_departure_time"));
                    trainModel.setDest_arrival_time(finalObject.optString("dest_arrival_time"));
                    trainModel.setTravel_time(finalObject.optString("travel_time"));

                    TrainModel.From from = new TrainModel.From();
                    from.setName1(finalObject.getJSONObject("from").optString("name"));
                    from.setCode1(finalObject.getJSONObject("from").optString("code"));
                    trainModel.setFrom(from);



                    TrainModel.To to = new TrainModel.To();
                    to.setName2(finalObject.getJSONObject("to").optString("name"));
                    to.setCode2(finalObject.getJSONObject("to").optString("code"));
                    trainModel.setTo(to);

                    List<TrainModel.Classes> classesList= new ArrayList<>();
                    List<TrainModel.Days> daysList= new ArrayList<>();
                    for (int c = 0; c < finalObject.getJSONArray("classes").length(); c++) {
                        TrainModel.Classes classes = new TrainModel.Classes();
                        classes.setClass_code(finalObject.getJSONArray("classes").getJSONObject(c).optString("class-code"));
                        classes.setAvailable(finalObject.getJSONArray("classes").getJSONObject(c).optString("available"));
                        classesList.add(classes);
                    }
                    for (int d = 0; d < finalObject.getJSONArray("days").length(); d++) {
                        TrainModel.Days days = new TrainModel.Days();
                        days.setDay_code(finalObject.getJSONArray("days").getJSONObject(d).optString("day-code"));
                        days.setRuns(finalObject.getJSONArray("days").getJSONObject(d).optString("runs"));
                        daysList.add(days);
                    }
                    trainModel.setClassesList(classesList);
                    trainModel.setDaysList(daysList);
                    trainModelList.add(trainModel);
                }
                /*List<RailModel> railModelList;
                    JSONObject parentObject = new JSONObject(finalJson);
                   JSONArray parentArray = parentObject.getJSONArray("trains");
                    railModelList = new ArrayList<>();
                    for (int i = 0; i < parentObject.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        RailModel railModel = new RailModel();
                        railModel.setResponse_code(finalObject.optInt("response_code"));
                        railModel.setTotal(finalObject.optInt("total"));
                        List<RailModel.Train> trainList=new ArrayList<>();
                        for (int j = 0; j < finalObject.getJSONArray("train").length(); j++) {
                            //JSONArray finalArray = parentArray.getJSONArray(i);
                            // railModel.setTrain(finalArray.optJSONArray("train"));
                            RailModel.Train train = new RailModel.Train();
                            train.setNo(finalObject.optInt("no."));
                            train.setName(finalObject.optString("name"));
                            train.setNumber(finalObject.optInt("Number"));
                            train.setSrc_departure_time(finalObject.optString("src_departure_time"));
                            train.setDest_arrival_time(finalObject.optString("dest_arrival_time"));
                            train.setTravel_time(finalObject.optString("travel_time"));
                            RailModel.Train.From from = new RailModel.Train.From();
                            from.setName(finalObject.optString("name"));
                            from.setCode(finalObject.optString("code"));
                            RailModel.Train.To to = new RailModel.Train.To();
                            to.setName(finalObject.optString("name"));
                            to.setCode(finalObject.optString("code"));
                            List<RailModel.Train.Classes> classesList=new ArrayList<>();


                            for (int c = 0; c < finalObject.getJSONArray("classes").length(); c++) {
                                RailModel.Train.Classes classes = new RailModel.Train.Classes();
                              classes.setClass_code(finalObject.getJSONArray("classes").getJSONObject(c).optString("class-code"));
                                classes.setAvailable(finalObject.getJSONArray("classes").getJSONObject(c).optString("available"));
                                classesList.add(classes);
                            }
                            train.setClasses(classesList);
                            List<RailModel.Train.Days> daysList= new ArrayList<>();
                            for (int d = 0; d < finalObject.getJSONArray("days").length(); d++) {
                                RailModel.Train.Days days = new RailModel.Train.Days();
                                days.setDay_code(finalObject.getJSONArray("days").getJSONObject(d).optString("day-code"));
                                days.setRuns(finalObject.getJSONArray("days").getJSONObject(d).optString("runs"));
                                daysList.add(days);
                            }
                            train.setDays(daysList);

                            trainList.add(train);
                        }
                        railModel.setTrain(trainList);

                        railModelList.add(railModel);
                    }
                   // return railModelList;
                return trainList;

*/
            return trainModelList;
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrainModel> response) {
            super.onPostExecute(response);

        RailAdapter adapter = new RailAdapter(getApplicationContext(),R.layout.row,response);
        trainli.setAdapter(adapter);
        }

    }

    public class RailAdapter extends ArrayAdapter{
        private int resource;
        private List<TrainModel> trainModelList;
        private LayoutInflater inflater;
        public RailAdapter(Context context, int resource, List<TrainModel> objects) {
            super(context, resource, objects);
            trainModelList = objects;
            this.resource=resource;
            inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=inflater.inflate(resource,null);
            }
            ImageView train;
            TextView name;
            TextView number;
            TextView from;
            TextView to;
            TextView src_departure_time;
            TextView dest_arraival_Time;
            TextView travel_time;
            TextView days;
            TextView classes;
            train =(ImageView) convertView.findViewById(R.id.imageView);
            name=(TextView) convertView.findViewById(R.id.name);
            number=(TextView) convertView.findViewById(R.id.number);
            from=(TextView)convertView.findViewById(R.id.from);
            to=(TextView)convertView.findViewById(R.id.to);
            src_departure_time=(TextView)convertView.findViewById(R.id.src_departure_time);
            dest_arraival_Time=(TextView)convertView.findViewById(R.id.dest_arrival_time);
            travel_time=(TextView) convertView.findViewById(R.id.travel_time);
            days=(TextView)convertView.findViewById(R.id.days);
            classes=(TextView)convertView.findViewById(R.id.classes);

            name.setText("train name:"+trainModelList.get(position).getName());
            number.setText("train number:"+trainModelList.get(position).getNumber());
            from.setText("from: "+trainModelList.get(position).getFrom().getName1());
            to.setText("to: "+trainModelList.get(position).getTo().getName2());
            src_departure_time.setText("source depart: "+trainModelList.get(position).getSrc_departure_time());
            dest_arraival_Time.setText("destination reach: "+trainModelList.get(position).getDest_arrival_time());
            travel_time.setText("total duration: "+trainModelList.get(position).getTravel_time());
            StringBuffer stringBuffer= new StringBuffer();
            StringBuffer stringBuffer1=new StringBuffer();
            for (TrainModel.Classes classes1: trainModelList.get(position).getClassesList()){
                if((classes1.getAvailable()).equals("Y"))
                {
                    stringBuffer.append(classes1.getClass_code()+" ");
                }
            }
            for(TrainModel.Days days1: trainModelList.get(position).getDaysList()){
                if((days1.getRuns()).equals("Y"))
                {
                    stringBuffer1.append(days1.getDay_code()+" ");
                }
            }
            classes.setText("classes: "+stringBuffer);
            days.setText("days: "+stringBuffer1);
            return convertView;
        }
    }
}
