package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants.BASE_URL;

/**
 * Created by abhijit on 2/2/2017.
 */
public class forgotPassword extends AppCompatActivity{
    databaseHelper mydb;
    Button send;
    EditText emailpass;
    BackgroundTask backgroundTask;
    public final static String EXTRA_EMAIL="com.example.abhijit.helloandroid.email";

    public final static String EXTRA_PASS="com.example.abhijit.helloandroid.pass";

    public final static String EXTRA_CONTACT="com.example.abhijit.helloandroid.contact";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Toolbar mtoolbar= (Toolbar)findViewById(R.id.toolbar7);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // mydb = new databaseHelper(this);
        send = (Button) findViewById(R.id.getpassid);
        emailpass = (EditText) findViewById(R.id.passemailid);

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

    public void search(View view) {
        String email = emailpass.getText().toString();
        if (backgroundTask != null){
            backgroundTask = null;


        }
        backgroundTask = new BackgroundTask(forgotPassword.this);

        if (email.length() > 0) {
            //if (!(mydb.emailfind(email))) {

               // Cursor res= mydb.givepassphone(email);
               // if(res.moveToFirst())
              //  {
                //    String pass=res.getString(1);
                  //  String contact=res.getString(2);
                    //String nameto = res.getString(3);
                    //tring subject="your new password";
                   // String message="hello "+nameto+"....your new password as requested is "+pass+" ...please change the password after login as this is a temporary password.";
                   // if(pass.length()>0) {
                       // Intent intent=new Intent(Intent.ACTION_SEND);
                       // intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                       // intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                        //intent.putExtra(Intent.EXTRA_TEXT,message);
                        //intent.setType("meassge/rfc822");
                        //startActivity(Intent.createChooser(intent,"choose:"));
                        backgroundTask.execute(email);
                       /* Intent intent = new Intent(getApplicationContext(), selectgivePass.class);
                        intent.putExtra(EXTRA_PASS,pass);
                        intent.putExtra(EXTRA_CONTACT,contact);
                        intent.putExtra(EXTRA_EMAIL,email);
                        startActivity(intent);

                    }
                    }
               else
                {
                    Toast.makeText(forgotPassword.this,"no values selected",Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(forgotPassword.this, "no such email is registered", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(forgotPassword.this, "enter email id", Toast.LENGTH_LONG).show();
        */}
    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        //  JSONObject object;
       // String method;
       // AlertDialog alertDialog;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String forgotpass_url=BASE_URL+"forgotpass.php";

            String email = params[0];
            try {
                URL url = new URL(forgotpass_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((forgotpass_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(forgotpass_url + "\n");
                }
                String result = stringBuilder.toString();



                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                if ((object.optInt("code")) == 200) {
                    Toast.makeText(forgotPassword.this, "Your password has been mailed", Toast.LENGTH_LONG).show();
                } else if ((object.optInt("code")) == 201) {
                    String msg = object.optString("msg");
                    Toast.makeText(forgotPassword.this, msg, Toast.LENGTH_LONG).show();

                } else if ((object.optInt("code")) == 202) {
                    if((object.optString("msg")).equals("error:database")){
                        String msg = object.optString("msg");
                        Toast.makeText(forgotPassword.this, msg, Toast.LENGTH_LONG).show();
                    }
                     if((object.optString("msg")).equals("error:mail")){
                        String msg = object.optString("msg");
                        Toast.makeText(forgotPassword.this, msg, Toast.LENGTH_LONG).show();
                    }
                    }


            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        }

    }