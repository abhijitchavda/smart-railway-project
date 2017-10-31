package com.example.abhijit.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.TrainRescheduleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijit on 4/4/2017.
 */

public class Listview_Rescheduletrain extends AppCompatActivity {
    String jsonres;
    ListView rescheduletrli;
    BackgroundTask backgroundTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_reschedule_train);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar24);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        rescheduletrli=(ListView)findViewById(R.id.rescheduletrli);
        jsonres = getIntent().getStringExtra(RescheduleTrain.EXTRA_RESCHEDULETRAIN);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Listview_Rescheduletrain.this);
        backgroundTask.execute(jsonres);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),CancelRes.class);
            startActivity(intent);
            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public class BackgroundTask extends AsyncTask<String,Void,List<TrainRescheduleModel>> {
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
        protected List<TrainRescheduleModel> doInBackground(String... params) {
            try {
                String finalJson= params[0];

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("trains");

                List<TrainRescheduleModel> trainRescheduleModelList= new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TrainRescheduleModel trainRescheduleModel= new TrainRescheduleModel();

                    trainRescheduleModel.setName(finalObject.optString("name"));
                    trainRescheduleModel.setNumber(finalObject.optString("number"));
                    trainRescheduleModel.setRescheduled_time(finalObject.optString("rescheduled_time"));
                    trainRescheduleModel.setTime_diff(finalObject.optString("time_diff"));


                    trainRescheduleModelList.add(trainRescheduleModel);
                    //trainModelList.add(trainModel);
                }

                return trainRescheduleModelList;
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrainRescheduleModel> response) {
            super.onPostExecute(response);

            RailRescheduleAdapter adapter = new RailRescheduleAdapter(getApplicationContext(),R.layout.row_reschedule,response);
            rescheduletrli.setAdapter(adapter);

        }

    }

    public class RailRescheduleAdapter extends ArrayAdapter {
        private int resource;
        private List<TrainRescheduleModel> trainRescheduleModelList;
        private LayoutInflater inflater;
        public RailRescheduleAdapter(Context context, int resource, List<TrainRescheduleModel> objects) {
            super(context, resource, objects);
            trainRescheduleModelList = objects;
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
            TextView time;
            TextView time_diff;
            TextView number;
            train =(ImageView) convertView.findViewById(R.id.imageView);
            name=(TextView) convertView.findViewById(R.id.reschedulename);
            number=(TextView)convertView.findViewById(R.id.reschedulenumber);
            time=(TextView)convertView.findViewById(R.id.rescheduletime);
            time_diff=(TextView)convertView.findViewById(R.id.rescheduletimediff);

            name.setText("Train Name : "+trainRescheduleModelList.get(position).getName());
            number.setText("Train Number : "+trainRescheduleModelList.get(position).getNumber());
            time.setText("Rescheduled Time : "+trainRescheduleModelList.get(position).getRescheduled_time());
            time_diff.setText("Time Difference"+trainRescheduleModelList.get(position).getTime_diff());
            return convertView;
        }
    }



}
