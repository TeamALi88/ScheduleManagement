package com.example.wayney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class login1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView forget_psw = (TextView) findViewById(R.id.forget_psw);
        forget_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login1Activity.this,forgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login1Activity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected (MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish(); // back button
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

}
