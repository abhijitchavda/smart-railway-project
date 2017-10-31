package com.example.abhijit.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by abhijit on 3/17/2017.
 */

public class GenTicketEntryType extends AppCompatActivity {
    public final static String EXTRA_TYPE="com.example.abhijit.helloandroid.type";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_ticket_entry_type);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar14);
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
            Intent intent = new Intent(getApplicationContext(),BeginJourney.class);
          startActivity(intent);

         //   finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public void searchtrgen(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Search_train_gen.class);
        startActivity(intent);
    }
    public void manualtype(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ReservationTicket.class);
        intent.putExtra(EXTRA_TYPE,"GENERAL");
        startActivity(intent);
    }
}
