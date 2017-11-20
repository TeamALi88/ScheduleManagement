package com.example.haoji.dailyActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.haoji.Database;
import com.example.haoji.R;

import java.util.ArrayList;


import static com.tencent.open.utils.Global.getContext;


public class MainActivity extends AppCompatActivity{
    private SQLiteOpenHelper moh;
    private SQLiteDatabase sd;
    private ArrayList<day1> datelist;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);


        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbOneday:
                        FragmentChat chat = new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
                        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
                        break;
                    case R.id.rbThreeDay:
                        FragmentThreeDay threeday=new FragmentThreeDay();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,threeday).commit();
                        ThreedayFragmentRecyclerview threedaylist = new  ThreedayFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, threedaylist).commit();
                        break;
                    case R.id.rbWeek:
                        FragmentWeek week = new FragmentWeek();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,week).commit();
                        WeekFragmentRecyclerview weeklist = new  WeekFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, weeklist).commit();
                        break;

                    case R.id.rbMonth:
                        FragmentMonth month = new FragmentMonth();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, month)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });





    }


}