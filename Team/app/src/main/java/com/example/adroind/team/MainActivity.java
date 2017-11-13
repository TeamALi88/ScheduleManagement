package com.example.adroind.team;

import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        FragmentChat chatlist = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, chatlist).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);


        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbOneday:
                        FragmentChat chat = new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
                         ChatListView chatlist = new ChatListView();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, chatlist).commit();
                        break;
                    case R.id.rbThreeDay:
                        FragmentThreeDay threeday=new FragmentThreeDay();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,threeday).commit();
                       ThreeListView  threelistview=new ThreeListView();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2,threelistview).commit();
                        break;
                    case R.id.rbWeek:
                        FragmentWeek week = new FragmentWeek();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,week).commit();
                       WeekListView weeklistview = new WeekListView();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2,weeklistview).commit();
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