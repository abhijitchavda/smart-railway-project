package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.TrainGenRouteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijit on 3/19/2017.
 */

public class Listview_gen_train_route extends AppCompatActivity {
    String train_num,src_station,jsonres;
    ListView trainligenroute;
    BackgroundTask backgroundTask;
    public final static String EXTRA_TRAIN_NUMBER_2="com.example.abhijit.helloandroid.trainnumberpass2";
    public final static String EXTRA_FROM_STATION_2="com.example.abhijit.helloandroid.fromstation2";
    public final static String EXTRA_TO_STATION_2="com.example.abhijit.helloandroid.tostation2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_train_gen_route);
        //setContentView(R.layout.listview_demo);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar21);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        train_num = getIntent().getStringExtra(Gen_Train_Route.EXTRA_TRAIN_NUMBER_1);
        src_station=getIntent().getStringExtra(Gen_Train_Route.EXTRA_FROM_STATION_1);
        jsonres=getIntent().getStringExtra(Gen_Train_Route.EXTRA_JSON_RESPONSE);
        trainligenroute=(ListView)findViewById(R.id.trainligenroute);

        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Listview_gen_train_route.this);
        backgroundTask.execute(jsonres);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),Search_train_gen.class);
            startActivity(intent);
            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public class BackgroundTask extends AsyncTask<String,Void,List<TrainGenRouteModel>> {
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
        protected List<TrainGenRouteModel> doInBackground(String... params) {

            try {
                String finalJson= params[0];

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("route");

                List<TrainGenRouteModel> trainGenRouteModelList= new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                      TrainGenRouteModel trainGenRouteModel=new TrainGenRouteModel();
                    trainGenRouteModel.setFullname(finalObject.optString("fullname"));
                    trainGenRouteModel.setCode(finalObject.optString("code"));
                    trainGenRouteModel.setScharr(finalObject.optString("scharr"));
                    //trainGenModel.setScharr(finalObject.optString("scharr"));
                    //trainModel.setDest_arrival_time(finalObject.optString("dest_arrival_time"));
                    //trainModel.setTravel_time(finalObject.optString("travel_time"));
                    trainGenRouteModelList.add(trainGenRouteModel);

                    //trainModelList.add(trainModel);
                }

                return trainGenRouteModelList;
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrainGenRouteModel> response) {
            super.onPostExecute(response);

            RailGenRouteAdapter adapter = new RailGenRouteAdapter(getApplicationContext(),R.layout.row_route,response);
            trainligenroute.setAdapter(adapter);
            trainligenroute.setOnItemClickListener(new ItemList());
        }

    }

    class ItemList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ViewGroup vg =(ViewGroup)view;
            TextView name=(TextView)vg.findViewById(R.id.name);
            String fromname=name.getText().toString();
            Intent intent = new Intent(getApplicationContext(),Gen_ticket_show.class);
            intent.putExtra(EXTRA_FROM_STATION_2,src_station);
            intent.putExtra(EXTRA_TRAIN_NUMBER_2,train_num);
            intent.putExtra(EXTRA_TO_STATION_2,fromname);
            startActivity(intent);

            //Toast.makeText(Listview_gen_trains.this,name.getText().toString()+"---"+number.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
    public class RailGenRouteAdapter extends ArrayAdapter {
        private int resource;
        private List<TrainGenRouteModel> trainGenRouteModelList;
        private LayoutInflater inflater;
        public RailGenRouteAdapter(Context context, int resource, List<TrainGenRouteModel> objects) {
            super(context, resource, objects);
            trainGenRouteModelList = objects;
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
            //TextView code;
            //TextView from;
            // TextView to;
            //TextView src_departure_time;
            TextView dest_arraival_Time;
            //TextView travel_time;
            //TextView days;
            //TextView classes;
            train =(ImageView) convertView.findViewById(R.id.imageView);
            name=(TextView) convertView.findViewById(R.id.name);
          //  code=(TextView) convertView.findViewById(R.id.code);
            //from=(TextView)convertView.findViewById(R.id.from);
            //to=(TextView)convertView.findViewById(R.id.to);
            //src_departure_time=(TextView)convertView.findViewById(R.id.src_departure_time);
            dest_arraival_Time=(TextView)convertView.findViewById(R.id.dest_arrival_time);
            //travel_time=(TextView) convertView.findViewById(R.id.travel_time);
            // days=(TextView)convertView.findViewById(R.id.days);
            //classes=(TextView)convertView.findViewById(R.id.classes);

            name.setText("station:"+trainGenRouteModelList.get(position).getFullname()+"-"+trainGenRouteModelList.get(position).getCode());
           // number.setText("train number:"+trainGenModelList.get(position).getNumber());
            //src_departure_time.setText("scheduled departure: "+trainGenModelList.get(position).getSchdep());
            dest_arraival_Time.setText("destination reach: "+trainGenRouteModelList.get(position).getScharr());

            return convertView;
        }
    }

}

