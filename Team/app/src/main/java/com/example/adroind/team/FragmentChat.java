package com.example.adroind.team;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentChat extends Fragment {
    public static String toString(Date date) {

        String time;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        time = formater.format(date);
        return time;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.chat, container, false);

        TextView day1 = (TextView) view.findViewById(R.id.day1);
        Calendar c = Calendar.getInstance();
        Date d1 = c.getTime();
       /* TextView tv=new TextView();
        tv.setText(str);*/
        String str = toString(d1);
        day1.setText(str);
        return view;

    }



}



