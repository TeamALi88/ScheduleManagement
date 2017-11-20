package com.example.haoji.dailyActivity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoji.Database;
import com.example.haoji.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/11/17.
 */

public class OnedayFragmentRecyclerview extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Database dbhelper;
    private int year;
    private int month;
    private int day;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.recyclerview, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mAdapter = new MyAdapter(getData());

        //database initialize
        dbhelper = new Database(this, "HaojiDatabase.db", null, 1);

        //Calendar initialize
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {

                if (position % 2 == 0) {
                    return 1;
                } else {
                    return 3;
                }

            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity()));

        return view;
    }


    private ArrayList<String> getData() {
       ArrayList<String> data=new ArrayList<>();
       SQLiteDatabase db = dbhelper.getWritableDatabase();
       Cursor cursor = db.rawQuery("select * from schedule where year="+year
               + " and month="+month
               + " and day="+day
               , null);
       if(cursor.moveToFirst()){
           do{
               String content = cursor.getString(cursor.getColumnIndex("content"));
               data.add(content);
           } while(cursor.moveToNext());
       }
       return data;
    }

}

