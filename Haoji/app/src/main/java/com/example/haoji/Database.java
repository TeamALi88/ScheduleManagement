package com.example.haoji;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by misdeer on 2017/11/17.
 */

public class Database extends SQLiteOpenHelper {
    public static final String CREATE_SCHEDULE = "create table schedule "
            + "(id integer primary key autoincrement, "
            + "content text, "
            + "year integer, "
            + "month integer, "
            + "day integer, "
            + "hour integer, "
            + "minute integer, "
            + "remind integer, "
            + "tag text)";
    private Context mContext;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion){

    }
}
