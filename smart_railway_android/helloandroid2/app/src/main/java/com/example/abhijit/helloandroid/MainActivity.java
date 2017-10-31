package com.example.abhijit.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models.Constants;

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

public class MainActivity extends AppCompatActivity {
    databaseHelper mydb;
    Button login,createacc,forpass;
    EditText email_;
    EditText password_;
    public static String email11;
   // String JSON_STRING;
    BackgroundTask backgroundTask;
    public final static String EXTRA_EMAIL1="com.example.abhijit.helloandroid.emailin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //mydb = new databaseHelper(this);
        email_= (EditText) findViewById(R.id.editText);
        password_ = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.button);
        createacc = (Button) findViewById(R.id.button2);
        forpass = (Button) findViewById(R.id.forgotpass);
        email_.setText("chavda.abhi@gmail.com");
        password_.setText("Abhi@1234");
        logincheck();
    }
public void forgotpassword(View view)
{
    Intent intent = new Intent (getApplicationContext(),forgotPassword.class);
    startActivity(intent);
}

    public void insert(View view)
    {
                        Intent intent = new Intent (getApplicationContext(),createAccount.class);
                        startActivity(intent);
    }

    public void logincheck ()
    {
        login.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = email_.getText().toString();
                        String password = password_.getText().toString();

                        if (backgroundTask != null){
                            backgroundTask = null;


                        }
                        backgroundTask = new BackgroundTask(MainActivity.this);
                        if(password.length()>0 && email.length()>0) {
                            //if (!(mydb.emailfind(email))) {
                              //  String pass = mydb.login(email);
                                //    if (password.equals(pass)) {

                                        backgroundTask.execute(email,password);
                                    //    Intent intent = new Intent(getApplicationContext(), changePassword.class);
                                      //  intent.putExtra(EXTRA_EMAIL1, email);
                                        //startActivity(intent);

                                        //Toast.makeText(MainActivity.this, "loged in", Toast.LENGTH_LONG).show();
                                  //  } else {
                                        //Toast.makeText(MainActivity.this, "invalid password", Toast.LENGTH_LONG).show();
                                    //}

                              //  }
                             //else
                               // Toast.makeText(MainActivity.this, "invalid email", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"please fill in all the details",Toast.LENGTH_LONG).show();
                    }
                    }
        );
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
           // alertDialog = new AlertDialog.Builder(ctx).create();
           // alertDialog.setTitle("account status");

        }

        @Override
        protected String doInBackground(String... params) {

            String login_url = Constants.LOGIN_URL;
//            String login_url = "http://192.168.42.149/login.php";
            // String forgotpass_url="http://10.0.2.2/forgotpass.php";
            String email = params[0];
            String password = params[1];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((login_url = bufferedReader.readLine()) != null) {
                    stringBuilder.append(login_url + "\n");
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
        protected void onPostExecute(String response) {
            try {
                if (TextUtils.isEmpty(response))
                    return;

                JSONObject object = new JSONObject(response);
                if ((object.optInt("code")) == 200) {
                    email11 = object.optString("email");
                    Toast.makeText(MainActivity.this, "Login done", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    //intent.putExtra(EXTRA_EMAIL1, email);
                    startActivity(intent);
                }
                else if ((object.optInt("code")) == 201) {
                  //  Toast.makeText(MainActivity.this, "IN FALSE", Toast.LENGTH_LONG).show();
                    if ((object.optString("msg")).equals( "wrong password")) {
                        String msg = object.optString("msg");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if ((object.optString("msg")).equals("wrong email")) {
                        String msg = object.optString("msg");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                    }
                }
                // else if((object.optInt("code")) == 201 && object.optString("msg") == "wrong email") {
                //  String msg = object.getString("msg");
                // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();}


        }
            catch(JSONException e){
                e.printStackTrace();
               Toast.makeText(MainActivity.this, "Login failed----", Toast.LENGTH_LONG).show();
            }
        }
        }
    }

