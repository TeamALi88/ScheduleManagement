package com.example.haoji.dailyActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.haoji.R;

public class WeekListView extends Fragment {

    private ListView lv;
    private String Data[]={"北京","sh","fz","xm","hz","上海","福州","南宁","厦门","广西","广州","陕西","深圳","黑龙江"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.list_view , container, false);
        ListView listView = (ListView)view.findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,Data);
        listView.setAdapter(arrayAdapter);
        return view;

    }
}