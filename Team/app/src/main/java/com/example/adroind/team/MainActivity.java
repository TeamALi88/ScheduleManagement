package com.example.adroind.team;

import android.content.Context;
import android.content.Entity;
import android.net.http.HttpResponseCache;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    public class date {
        private String name;
        private int Sid;

        public date(String name) {
            this.name = name;

        }

        public String getName() {
            return name;
        }


    }

    public class dateAdapter extends ArrayAdapter<date> {
        private int resourceID;

        public dateAdapter(Context context, int textViewResourceID, List<date> objects) {
            super(context, textViewResourceID, objects);
            resourceID = textViewResourceID;


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            date d1 = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
            TextView d1Name = (TextView) view.findViewById(R.id.date_name);
            d1Name.setText(d1.getName());
            return view;
        }

    }

    private List<date> dateList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);


        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbOneday:
                        FragmentChat chat = new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();

                        break;
                    case R.id.rbThreeDay:
                        FragmentThreeDay threeday = new FragmentThreeDay();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, threeday).commit();

                        break;
                    case R.id.rbWeek:
                        FragmentWeek week = new FragmentWeek();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, week)
                                .commit();

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


        initdate();
        dateAdapter adapter = new dateAdapter(MainActivity.this, R.layout.list_view, dateList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);


    }

    public void initdate() {

        date da = new date("上午6时");
        dateList.add(da);
        date db = new date("上午7时");
        dateList.add(db);
        date dc = new date("上午8时");
        dateList.add(dc);
        date dd = new date("上午9时");
        dateList.add(dd);
        date de = new date("上午10时");
        dateList.add(de);
        date df = new date("上午11时");
        dateList.add(df);
        date dg = new date("上午12时");
        dateList.add(dg);
        date dh = new date("下午13时");
        dateList.add(dh);
        date di = new date("下午14时");
        dateList.add(di);
        date dj = new date("下午15时");
        dateList.add(dj);
        date dk = new date("下午16时");
        dateList.add(dk);
        date dl = new date("下午17时");
        dateList.add(dl);
        date dm = new date("下午18时");
        dateList.add(dm);
        date dn = new date("下午19时");
        dateList.add(dn);
        date dp = new date("下午20时");
        dateList.add(dp);
        date dq = new date("下午21时");
        dateList.add(dq);
        date dr = new date("下午22时");
        dateList.add(dr);
        date ds = new date("下午23时");
        dateList.add(ds);


    }

}

  /*public String[] analyticJson(String result){
        try {
            String[] ss=new String[5];//为了演示方便让其返回String[]
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            JSONArray jsonArray=jsonObject.getJSONArray("info");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = (JSONObject)jsonArray.opt(i);
                ss[i]=jo.getInt("id")+"  "+jo.getString("name")+"  "+jo.getString("地址");
            }
            return ss;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}*/

