package com.example.haoji.dailyActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.haoji.GlobalVariable;
import com.example.haoji.R;


public class read_userName extends AppCompatActivity {
    private TextView userName;
    private GlobalVariable app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        userName = (TextView)findViewById(R.id.userNameSider);
        app = (GlobalVariable) getApplication();
    }
    public void setInfo(GlobalVariable app){
        userName.setText(app.getUserName());
    }
    @Override
    protected void onStart(){
        super.onStart();
        app = (GlobalVariable) getApplication();
        setInfo(app);
    }
}

