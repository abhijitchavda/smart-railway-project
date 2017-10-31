package com.example.abhijit.helloandroid;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Created by abhijit on 1/30/2017.
 */
public class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "railway.db";
    public static final String TABLE_NAME = "";
    public static final String COL_1 = "email";
    public static final String COL_2 = "name";
    public static final String COL_3 = "password";
    public static final String COL_4 = "city";
    public static final String COL_5 = "contact_no";



    public databaseHelper (Context context)
{
    super(context,DATABASE_NAME,null,1);
    SQLiteDatabase db = this.getWritableDatabase();

}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+ "(email TEXT PRIMARY KEY,name TEXT,password TEXT,city TEXT,contact_no TEXT)");
        db.execSQL("insert into "+TABLE_NAME+" values ('chavda.abhijit@gmail.com','abhijit','Abhijit@12','ahmedabad','8530455194')");
        db.execSQL("insert into "+TABLE_NAME+" values ('khyatimirani77@gmail.com','khyati Mirani','Khyati71','Dholka','8945671234')");
        db.execSQL("insert into "+TABLE_NAME+" values ('abhijitchavda1@gmail.com', 'abhijit ch','Abhijit','Dholka','8945671234')");
    }
  /*  public boolean insertData(String email,String name,String password,String city,String contact_no){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1, "chavda.abhijit@gmail.com");
        contentValues.put(COL_2,"abhijit");
        contentValues.put(COL_3,"abhijit");
        contentValues.put(COL_4,"ahmedabad");
        contentValues.put(COL_5,"8530455194");
        db.insert(TABLE_NAME,null,contentValues);

        };
        }*/


    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion ,int newVersion) {
db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }
public boolean insertit(String email,String name,String pass,String city,String contact)
{

    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(COL_1, "chavda.abhijit@gmail.com");
    contentValues.put(COL_2,"abhijit");
    contentValues.put(COL_3,"abhijit");
    contentValues.put(COL_4,"ahmedabad");
    contentValues.put(COL_5,"8530455194");
    db.insert(TABLE_NAME,null,contentValues);

    long result=db.insert(TABLE_NAME,null,contentValues);
    if (result == -1)
        return false;
    else
        return true;
}
    public String login(String email1)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res= db.rawQuery("select email,password from profile3",null);
        String a,b;
        b="not found";
        if(res.moveToFirst())
        {
            do{
                a=res.getString(0);
                if(a.equals(email1))
                {
                    b=res.getString(1);
                    break;
                }

            }while (res.moveToNext());
        }

            return b;
        }
    public Cursor givepassphone (String email1)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor demo=db.rawQuery("select email,contact_no from profile3 WHERE email='"+email1+"';",null);
        if(demo.moveToFirst())
        {
            String pass1=demo.getString(0);
            String pass2=demo.getString(1);
            int n=pass2.length();
            int x=n-5;
            String pass= pass1.substring(0,4)+pass2.substring(x,n);
            db.execSQL("UPDATE profile3 SET password='"+pass+"' WHERE email='"+email1+"';");
        }
        Cursor res= db.rawQuery("select email,password,contact_no,name from profile3 WHERE email='"+email1+"';",null);
        return res;
    }
    public Boolean passchange(String email1,String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE profile3 SET password='"+pass+"' WHERE email='"+email1+"';");
        return true;
    }
    public boolean emailfind(String email1)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res= db.rawQuery("select email from profile3",null);
        String a;
        int count=0;
        if(res.moveToFirst())
        {
            do{
                a=res.getString(0);
                if(a.equals(email1))
                {
                    count=1;
                    break;
                }

            }while (res.moveToNext());
        }

        if(count==1)
        {
            return false;
        }
        else
            return true;
    }
    }




