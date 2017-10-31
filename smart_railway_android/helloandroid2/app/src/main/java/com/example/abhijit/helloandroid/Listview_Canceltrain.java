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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.TrainCancelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijit on 4/3/2017.
 */

public class Listview_Canceltrain extends AppCompatActivity {
    String jsonres;
    ListView canceltrli;
    BackgroundTask backgroundTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_cancel_train);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar23);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        canceltrli=(ListView)findViewById(R.id.canceltrli);
        jsonres = getIntent().getStringExtra(CancelledTrain.EXTRA_CANCELLEDTRAIN);
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(Listview_Canceltrain.this);
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


    public class BackgroundTask extends AsyncTask<String,Void,List<TrainCancelModel>> {
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
        protected List<TrainCancelModel> doInBackground(String... params) {
            try {
                String finalJson= params[0];

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("trains");

                List<TrainCancelModel> trainCancelModelList= new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TrainCancelModel trainCancelModel= new TrainCancelModel();
                    //trainGenModel.setNo(finalObject.optInt("no"));


                    TrainCancelModel.Train train = new TrainCancelModel.Train();
                    train.setName(finalObject.getJSONObject("train").optString("name")); //setName1(finalObject.getJSONObject("from").optString("name"));
                    train.setNumber(finalObject.getJSONObject("train").optString("number"));
                    trainCancelModel.setTrain(train);


                    trainCancelModelList.add(trainCancelModel);
                    //trainModelList.add(trainModel);
                }

                return trainCancelModelList;
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrainCancelModel> response) {
            super.onPostExecute(response);

            RailCancelAdapter adapter = new RailCancelAdapter(getApplicationContext(),R.layout.row_cancel,response);
            canceltrli.setAdapter(adapter);

        }

    }

    public class RailCancelAdapter extends ArrayAdapter {
        private int resource;
        private List<TrainCancelModel> trainCancelModelList;
        private LayoutInflater inflater;
        public RailCancelAdapter(Context context, int resource, List<TrainCancelModel> objects) {
            super(context, resource, objects);
            trainCancelModelList = objects;
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
            train =(ImageView) convertView.findViewById(R.id.imageView);
            name=(TextView) convertView.findViewById(R.id.cancelname);
            number=(TextView)convertView.findViewById(R.id.cancelnumber);

           name.setText("Train Name : "+trainCancelModelList.get(position).getTrain().getName());
            number.setText("Train Number : "+trainCancelModelList.get(position).getTrain().getNumber());
            return convertView;
        }
    }
}
