package com.example.haoji.dailyActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.haoji.Database;
import com.example.haoji.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/17.
 */

public class ThreedayFragmentRecyclerview extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int year;
    private int month;
    private int day;
    private Database dbhelper;
    private SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // TODO 三日日程显示方式待商议
        View view= inflater.inflate(R.layout.recyclerview , container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mAdapter = new MyAdapter(getData());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity()));

        return  view;
    }


    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";

        String temp2=":00";

        for(int i =0;i<=99;i++) {
            if(i % 4 ==0)
            {
                data.add(i/4 + temp2);
            }
            else
            {
                data.add(i/4 + temp);
            }
        }
        return data;
    }
}

