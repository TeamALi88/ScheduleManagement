package com.example.haoji.dailyActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.haoji.Database;
import com.example.haoji.R;

// import org.achartengine.ChartFactory;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class pieChartActivity extends AppCompatActivity {
    private GraphicalView view;
    private CategorySeries cs;

    public Handler handler;
    private DefaultRenderer renderer;

    private SimpleSeriesRenderer ssr1;
    private SimpleSeriesRenderer ssr2;
    private SimpleSeriesRenderer ssr3;
    private SimpleSeriesRenderer ssr4;
    private SimpleSeriesRenderer ssr5;

    private Database dbhelper;
    private SQLiteDatabase db;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        setContentView(R.layout.activity_pie_chart);
        LinearLayout ll = (LinearLayout) findViewById(R.id.id_layout);
    }
}
