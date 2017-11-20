package com.example.adroind.team;

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

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/17.
 */

public class OnedayFragmentRecyclerview extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.recyclerview, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mAdapter = new MyAdapter(getData());

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
       ArrayList<String>data=new ArrayList<>();
       String temp = " safafasfafafasfafdsdsdfdsfdsfsfdsfsf";

        String temp2=":00";

        for(int i =0;i<=49;i++) {
            if(i % 2 ==0)
            {
                data.add(i/2 + temp2);
            }
            else
            {
                data.add(i/2 + temp);
            }
        }
        return data;
    }

    }

