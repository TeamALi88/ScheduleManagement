package com.example.haoji.dailyActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoji.Database;
import com.example.haoji.R;
import  com.example.haoji.dailyActivity.day1;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Administrator on 2017/11/17.
 */

@SuppressLint("ValidFragment")
public class OnedayFragmentRecyclerview extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Database dbhelper;
    private SQLiteDatabase db;
    private int year;
    private int month;
    private int day;
    public OnedayFragmentRecyclerview(){

    }
    public OnedayFragmentRecyclerview(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // TODO 单日日程显示不对齐
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        mAdapter = new MyAdapter(getData());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),newPlan.class);
                startActivity(intent);
            }
        });

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

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity() ,newPlan.class);
                intent.putExtra("from", "Main");//调用的时候要把"Main"改成其他的就行
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity()));

        return view;
    }


    private ArrayList<String> getData() {

        //Calendar initialize
        Calendar calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH)+1;
//        day = calendar.get(Calendar.DAY_OF_MONTH);

        //database initialize
        dbhelper = new Database(this.getContext(), "HaojiDatabase.db", null, 1);
        db = dbhelper.getWritableDatabase();

        ArrayList<String> data = new ArrayList<>();

       /* mAdapter.setClickListener(new MyAdapter.OnItemClickListner(){

            public void OnItemClick(View view, int position) {

                Toast.makeText(getActivity(), "Item " + position + " clicked:", Toast.LENGTH_SHORT).show();
                //跳转动作
            }
        });*/



        Cursor cursor = db.rawQuery("select * from schedule where year = "+year
                + " and month = "+month
                + " and day = " +day,null);
        //Log.d("getData()", ""+cursor.getCount());
        //Log.d("getData():Date", "year:"+year+"month:"+month+"day:"+day);

        if(cursor.moveToFirst()){
            do{
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String hour = cursor.getString(cursor.getColumnIndex("hour"));
                String minute = cursor.getString(cursor.getColumnIndex("minute"));
                data.add(hour+":"+minute);
                data.add(content);

                ;
                //Log.d("getData()", "addData");
            } while(cursor.moveToNext());
        }
        return data;
    }



}