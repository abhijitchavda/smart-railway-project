package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by abhijit on 3/7/2017.
 */

public class ResTicketEntryType extends AppCompatActivity {
 String email= Homepage.email;
    public final static String EXTRA_TYPE="com.example.abhijit.helloandroid.type";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_ticket_entry_type);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar9);
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
            //Intent intent = new Intent(getApplicationContext(),Homepage.class);
            //startActivity(intent);

            finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void pnrnotype(View view)
    {
        Intent intent = new Intent(getApplicationContext(),PnrNoTicket.class);
        startActivity(intent);
    }
    public void manualtype(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ReservationTicket.class);
        intent.putExtra(EXTRA_TYPE,"RESERVATION");
        startActivity(intent);
    }
}
