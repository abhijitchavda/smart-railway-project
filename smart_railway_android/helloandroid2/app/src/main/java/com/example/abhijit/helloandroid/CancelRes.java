package com.example.abhijit.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by abhijit on 4/3/2017.
 */

public class CancelRes extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_res);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar19);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),Homepage.class);
            startActivity(intent);

            //finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
public void cancelled (View view)
{
    Intent intent = new Intent(getApplicationContext(),CancelledTrain.class);
    startActivity(intent);
}
    public void reschedule(View view)
    {
        Intent intent = new Intent(getApplicationContext(),RescheduleTrain.class);
        startActivity(intent);

    }
}
