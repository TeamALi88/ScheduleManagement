package com.example.haoji.dailyActivity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haoji.Database;
import com.example.haoji.R;
import com.mob.commons.a;
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
    private SimpleSeriesRenderer ssr6;
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
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("数据分析");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        LinearLayout ll = (LinearLayout) findViewById(R.id.id_layout);
        view = ChartFactory.getPieChartView(this, getData(), getRenderer());
        ll.addView(view);

        Timer time = new Timer();

        handler = new Handler() {// 这里的Handler实例将配合下面的Timer实例，完成定时更新图表的功能
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    init();// 刷新图表具体方法 Handler将此并入主线程
                }
                //  super.handleMessage(msg);
            }
        };
        TimerTask task = new TimerTask() {
            public void run() {// 通过消息更新
                Log.i("task", " task ok ");
                Message message = new Message();
                message.what = 1;// 消息定义标志
                handler.sendMessage(message);
            }
        };

        time.schedule(task, 1, 1000);// 执行任务, 一秒一次


    }


    //模拟动态赋值，
    public void init() {


        cs.clear();//清空之前的数据
        // 设置种类名称和对应的数值，前面是（key,value）键值对

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dbhelper = new Database(this, "HaojiDatabase.db", null, 1);
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from schedule where year = " + year + " and month = " + month + " and day = " + day, null);

        if (cursor.moveToFirst()) {
            int x1 = 0;
            int x2 = 0;
            int x3 = 0 ;
            int x4 = 0;
            int x5 = 0;
            int x6 = 0;
            double sum=0;
            do {
                String tagx = cursor.getString(cursor.getColumnIndex("tag"));
                if (tagx.equals("娱乐")) x1++;
                if (tagx.equals("会议")) x2++;
                if (tagx.equals("未分类")) x3++;
                if (tagx.equals("社交")) x4++;
                if (tagx.equals("工作")) x5++;
                if (tagx.equals("学习")) x6++;
                //Log.d("getData()", "addData");
            } while (cursor.moveToNext());

            sum = x1+x2+x3+x4+x5+x6;
            double R1 = x1 / sum;
            double R2 = x2 / sum;
            double R3 = x3 / sum;
            double R4 = x4 / sum;
            double R5 = x5 / sum;
            double R6 = x6 / sum;
            cs.add("娱乐" ,R1);
            cs.add("会议",R2);
            cs.add("未分类",R3);
            cs.add("社交", R4);
            cs.add("工作", R5);
            cs.add("学习", R6);

            //让底部说明标签显示,如果不行,那就只会在初始化的时候显示一次
            renderer.setShowLabels(true);//设置显示标签
           // renderer.setShowLegend(true);//显示底部说明标签
            renderer.setShowLegend(false);
            renderer.setLabelsTextSize(40);//设置标签字体大小，
            renderer.setLabelsColor(Color.BLACK);
            renderer.setAntialiasing(true);//消失锯齿
            renderer.setApplyBackgroundColor(true);//想要添加背景要先申请
            renderer.setBackgroundColor(Color.WHITE);
            view.repaint();//重画,不写就不会显示动态变化
        }
    }
    //创建饼图

    public CategorySeries getData() {

        cs = new CategorySeries("数据分析");
        cs.add("会议", 10);
        cs.add("学习", 10);
        cs.add("娱乐", 50);
        cs.add("社交", 10);
        cs.add("工作", 10);
        cs.add("未分类", 10);
        return cs;
    }

    //创建渲染器
    public DefaultRenderer getRenderer() {
        //创建渲染器，描绘器对象
        renderer = new DefaultRenderer();

        ssr1 = new SimpleSeriesRenderer();
        ssr2 = new SimpleSeriesRenderer();
        ssr3 = new SimpleSeriesRenderer();
        ssr4 = new SimpleSeriesRenderer();
        ssr5 = new SimpleSeriesRenderer();
        ssr6 = new SimpleSeriesRenderer();

        ssr1.setDisplayChartValuesDistance(30);
        ssr2.setDisplayChartValuesDistance(30);
        ssr3.setDisplayChartValuesDistance(30);
        ssr4.setDisplayChartValuesDistance(30);
        ssr5.setDisplayChartValuesDistance(30);
        ssr6.setDisplayChartValuesDistance(30);

        ssr1.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
        ssr2.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
        ssr3.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
        ssr4.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
        ssr5.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
        ssr6.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比

        ssr1.setColor(Color.rgb(255,255,0));//娱乐
        ssr2.setColor(Color.rgb(34,139,34));
        ssr3.setColor(Color.rgb(0,134,139));
        ssr4.setColor(Color.rgb(64,224,205));
        ssr5.setColor(Color.rgb(227,23,13));
        ssr6.setColor(Color.rgb(255,128,0));

        renderer.addSeriesRenderer(ssr1);
        renderer.addSeriesRenderer(ssr2);
        renderer.addSeriesRenderer(ssr3);
        renderer.addSeriesRenderer(ssr4);
        renderer.addSeriesRenderer(ssr5);
        renderer.addSeriesRenderer(ssr6);
        renderer.setChartTitle("数据分析");
        renderer.setShowLabels(true);//设置显示标签
        renderer.setShowLegend(false);//显示底部说明标签
        renderer.setLabelsTextSize(40);//设置标签字体大小，
        renderer.setAntialiasing(true);//消失锯齿
        renderer.setApplyBackgroundColor(true);//想要添加背景要先申请
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setChartTitleTextSize(100);
        renderer.setDisplayValues(true); //显示数据,这个不写就不会显示出百分比。。
        //renderer.setZoomButtonsVisible(true); //显示缩小放大图标

        return renderer;
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
