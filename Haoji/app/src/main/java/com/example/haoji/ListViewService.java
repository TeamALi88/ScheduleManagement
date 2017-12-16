package com.example.haoji;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HP on 2017/12/15.
 * 设置在桌面控件中listView的用法
 */

public class ListViewService extends RemoteViewsService {
    private Database dbhelper;
    private SQLiteDatabase db;
    private int year;
    private int month;
    private int day;
    private static ArrayList<String> myNotes;//存放今日所有的日程

    private ArrayList<String> getData() {

        //Calendar initialize
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //database initialize
        dbhelper = new Database(this.getApplicationContext(), "HaojiDatabase.db", null, 1);
        db = dbhelper.getWritableDatabase();

        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from schedule where year = "+year
                + " and month = "+month
                + " and day = " +day,null);
        //Log.d("getData()", ""+cursor.getCount());
        //Log.d("getData():Date", "year:"+year+"month:"+month+"day:"+day);

        if(cursor.moveToFirst()){
            do{
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String hour = cursor.getString(cursor.getColumnIndex("hour"));
                String minute = cursor.getString(cursor.getColumnIndex("minute"));
                data.add(hour+":"+minute+" "+content);

                //Log.d("getData()", "addData");
            } while(cursor.moveToNext());
        }
        return data;
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private Context mContext;
        private int mAppWidgetId;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public RemoteViews getViewAt(int position) {
            //ListView的item的自定义布局文件
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_view);
            //设置每条日程的显示
            rv.setTextViewText(R.id.one_note,myNotes.get(position));
//            Intent intent = new Intent();
//            rv.setOnClickFillInIntent(R.id.table_list, intent);
            return rv;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public void onDestroy() {

        }
        @Override
        public RemoteViews getLoadingView() {
            //返回空时，按默认显示”正在加载数据“
            return null;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onCreate() {
            //完成初始数据装入的工作
            myNotes=getData();
        }
        @Override
        public void onDataSetChanged() {

        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getCount() {
//			Log.i("zl", "WidgetFactory, getCount ");
            return myNotes.size();
        }
    }
}
