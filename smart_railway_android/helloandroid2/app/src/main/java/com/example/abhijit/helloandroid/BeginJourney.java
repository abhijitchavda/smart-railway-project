package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by abhijit on 2/27/2017.
 */

public class BeginJourney extends AppCompatActivity {
    String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_journey);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        email = getIntent().getStringExtra(Homepage.EXTRA_EMAIL2);

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
    public void reservation(View view){
        Intent intent = new Intent(getApplicationContext(),ReservationTicket.class);
        startActivity(intent);
    }
    public void general(View view){
        Intent intent = new Intent(getApplicationContext(),GenTicketEntryType.class);
        //intent.putExtra(EXTRA_EMAIL4, email);
        startActivity(intent);

    }
}
