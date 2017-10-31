package com.example.abhijit.helloandroid;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by abhijit on 3/31/2017.
 */

public class ShareLocationContact extends AppCompatActivity {
    String namecsv="";
    String phonecsv="";

    String namearray[];
    String phonearray[];

    ListView lv1;
    public final static String EXTRA_CONTACT_NAME="com.example.abhijit.helloandroid.contactname";
    public final static String EXTRA_CONTACT_NUMBER="com.example.abhijit.helloandroid.contactnumber";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_location_contact);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar18);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        lv1=(ListView)findViewById(R.id.contacts);

        Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(phones.moveToNext()){
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber= phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(name!=null)
            {
                namecsv += name + ",";
                phonecsv += phoneNumber + ",";
            }
        }
        phones.close();

        namearray= namecsv.split(",");
        phonearray= phonecsv.split(",");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,namearray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        lv1.setAdapter(adapter);
       lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> ard0, View arg1, int arg2, long arg3) {
               String phoneno=phonearray[arg2];
               String contactname=namearray[arg2];

               Intent intent = new Intent(getApplicationContext(),ShareLatLong.class);
               intent.putExtra(EXTRA_CONTACT_NAME,contactname);
               intent.putExtra(EXTRA_CONTACT_NUMBER,phoneno);
               startActivity(intent);

               //Toast.makeText(getApplicationContext(),"-"+phoneno+"-"+contactname,Toast.LENGTH_LONG).show();
           }
       });
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

}
