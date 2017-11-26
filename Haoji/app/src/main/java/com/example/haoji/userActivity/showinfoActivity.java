package com.example.haoji.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoji.GlobalVariable;
import com.example.haoji.R;
import com.example.haoji.dailyActivity.dailyActivity;

public class showinfoActivity extends AppCompatActivity {
    private GlobalVariable app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        TextView userName = (TextView)findViewById(R.id.text_view5);
        TextView userPhone = (TextView)findViewById(R.id.text_view6);
        TextView qqNum = (TextView)findViewById(R.id.text_view7);
        TextView wbNum = (TextView)findViewById(R.id.text_view8);
        Button backLogin = (Button)findViewById(R.id.backLogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        app = (GlobalVariable) getApplication();
        userName.setText(app.getUserName());
        userPhone.setText(app.getUserPhone());
        qqNum.setText(app.getQqNum());
        wbNum.setText(app.getWbNum());




        FloatingActionButton edit_info=(FloatingActionButton) findViewById(R.id.edit_info);
        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(showinfoActivity.this,"test",Toast.LENGTH_SHORT);
                Intent intent = new Intent(showinfoActivity.this,changePasswordActivity.class);
                startActivity(intent);
            }
        });
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setState(0);
                Intent intent = new Intent(showinfoActivity.this,login1Activity.class);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                Intent intent = new Intent(showinfoActivity.this , dailyActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
