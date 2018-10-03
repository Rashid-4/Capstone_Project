package com.example.rashidalikhan.firebasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MySqlLiteHelper extends SQLiteOpenHelper{

    private static final int dbv =1;
    private static final String dbname = "LPU_DB";

    Context ct;
    MySqlLiteHelper(Context c)
    {
        super(c,dbname,null,dbv);
        ct =c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        String s = "create table lpu (id INTEGER PRIMARY KEY AUTOINCREMENT,name text,course text,branch text,phone text,att integer)";

        sqLiteDatabase.execSQL(s);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public void AddStudent(String Name,String Course,String Branch,String Phone,int Att)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", Name);
        cv.put("course",Course);
        cv.put("branch",Branch);
        cv.put("phone",Phone);
        cv.put("att",Att);
        db.insert("lpu",null, cv);
        Toast.makeText(ct,"Student Data Added...", Toast.LENGTH_SHORT).show();


    }
    public Cursor GetStudentData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from lpu",null);
        return res;
    }

}
