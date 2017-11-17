package com.example.haoji.dailyActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.haoji.Database;
import com.example.haoji.R;

import java.util.Calendar;

public class newPlan extends AppCompatActivity {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Database dbhelper;
    final int DATE_PICKER = 0;
    final int TIME_PICKER = 1;
    EditText editt_content;
    TextView textv_date;
    TextView textv_time;
    Button bt_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_plan_edit);

        //database helper
        dbhelper = new Database(this, "HaojiDatabase.db", null, 1);

        //initialize toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //initialize Views
        editt_content = (EditText) findViewById(R.id.new_plan_edit_content);
        textv_date = (TextView) findViewById(R.id.new_plan_edit_date);
        textv_time = (TextView) findViewById(R.id.new_plan_edit_time);
        bt_confirm = (Button) findViewById(R.id.new_plan_edit_confirm);

        //set default date & time
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MARCH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        textv_date.setText(year+"年"+month+"月"+day+"日");
        textv_time.setText((hour<10?"0":"")+hour+":"+(minute<10?"0":"")+minute);

        //Log.d("debug", "b1");
        textv_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDialog(DATE_PICKER);
            }
        });

        textv_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDialog(TIME_PICKER);
            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("content", editt_content.getText().toString());
                values.put("year", year);
                values.put("month", month);
                values.put("day", day);
                values.put("hour", hour);
                values.put("minute", minute);
                values.put("remind", 0);
                values.put("tag", "");
                db.insert("schedule", null, values);
                Toast.makeText(newPlan.this, "Success!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case DATE_PICKER:
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int _year, int _month, int _day){
                        year = _year;
                        month = _month;
                        day = _day;
                        textv_date.setText(year+"年"+month+"月"+day+"日");
                    }
                };
                return new DatePickerDialog(this,dateListener,year,month,day);
            case TIME_PICKER:
                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int _hour, int _minute){
                        hour = _hour;
                        minute = _minute;
                        textv_time.setText((hour<10?"0":"")+hour+":"+(minute<10?"0":"")+minute);
                    }
                };
                return new TimePickerDialog(this,timeListener,hour,minute,true);
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
