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
import android.widget.Toast;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.TrainGenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijit on 3/18/2017.
 */

public class Listview_gen_trains extends AppCompatActivity {
    String jsonres;
    String from;
    ListView trainligen;
    BackgroundTask backgroundTask;
    public final static String EXTRA_TRAIN_NUMBER="com.example.abhijit.helloandroid.trainnumberpass";
    public final static String EXTRA_FROM_STATION="com.example.abhijit.helloandroid.fromstation";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_general_trains);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar20);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        jsonres = getIntent().getStringExtra(Search_train_gen.EXTRA_JSON_TRAIN);
        from=getIntent().getStringExtra(Search_train_gen.EXTRA_STATION);
        trainligen=(ListView)findViewById(R.id.trainligen);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Listview_gen_trains.this);
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

    public class BackgroundTask extends AsyncTask<String,Void,List<TrainGenModel>> {
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
        protected List<TrainGenModel> doInBackground(String... params) {

            try {
                String finalJson= params[0];

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("train");

                List<TrainGenModel> trainGenModelList= new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TrainGenModel trainGenModel= new TrainGenModel();
                    //trainGenModel.setNo(finalObject.optInt("no"));
                    trainGenModel.setName(finalObject.optString("name"));
                    trainGenModel.setNumber(finalObject.optString("number"));
                   // trainModel.setSrc_departure_time(finalObject.optString("src_departure_time"));
                    trainGenModel.setSchdep(finalObject.optString("schdep"));
                    trainGenModel.setScharr(finalObject.optString("scharr"));
                    //trainModel.setDest_arrival_time(finalObject.optString("dest_arrival_time"));
                    //trainModel.setTravel_time(finalObject.optString("travel_time"));

                    trainGenModelList.add(trainGenModel);
                    //trainModelList.add(trainModel);
                }

                return trainGenModelList;
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrainGenModel> response) {
            super.onPostExecute(response);

            RailGenAdapter adapter = new RailGenAdapter(getApplicationContext(),R.layout.row_general,response);
            trainligen.setAdapter(adapter);
            trainligen.setOnItemClickListener(new ItemList());
        }

    }
    class ItemList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ViewGroup vg =(ViewGroup)view;
            TextView number=(TextView)vg.findViewById(R.id.number);
            String num=number.getText().toString();
            Intent intent = new Intent(getApplicationContext(),Gen_Train_Route.class);
            intent.putExtra(EXTRA_FROM_STATION,from);
            intent.putExtra(EXTRA_TRAIN_NUMBER,num);
            startActivity(intent);

            //Toast.makeText(Listview_gen_trains.this,name.getText().toString()+"---"+number.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
    public class RailGenAdapter extends ArrayAdapter {
        private int resource;
        private List<TrainGenModel> trainGenModelList;
        private LayoutInflater inflater;
        public RailGenAdapter(Context context, int resource, List<TrainGenModel> objects) {
            super(context, resource, objects);
            trainGenModelList = objects;
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
            //TextView from;
           // TextView to;
            TextView src_departure_time;
            TextView dest_arraival_Time;
            //TextView travel_time;
            //TextView days;
            //TextView classes;
            train =(ImageView) convertView.findViewById(R.id.imageView);
            name=(TextView) convertView.findViewById(R.id.name);
            number=(TextView) convertView.findViewById(R.id.number);
            //from=(TextView)convertView.findViewById(R.id.from);
            //to=(TextView)convertView.findViewById(R.id.to);
            src_departure_time=(TextView)convertView.findViewById(R.id.src_departure_time);
            dest_arraival_Time=(TextView)convertView.findViewById(R.id.dest_arrival_time);
            //travel_time=(TextView) convertView.findViewById(R.id.travel_time);
           // days=(TextView)convertView.findViewById(R.id.days);
            //classes=(TextView)convertView.findViewById(R.id.classes);

            name.setText("train name:"+trainGenModelList.get(position).getName());
            number.setText("train number:"+trainGenModelList.get(position).getNumber());
            src_departure_time.setText("scheduled departure: "+trainGenModelList.get(position).getSchdep());
            dest_arraival_Time.setText("scheduled arrival: "+trainGenModelList.get(position).getScharr());

            return convertView;
        }
    }

}
