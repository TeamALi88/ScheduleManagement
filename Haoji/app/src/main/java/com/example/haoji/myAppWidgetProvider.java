package com.example.haoji;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.haoji.dailyActivity.MainActivity;

/**
 * Created by HP on 2017/12/13.
 * 桌面控件
 * 定义创建，删除，更新的操作
 * 每次插入一个AppWidget，onReceive和onUpdate都会被执行
 */

public class myAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "myWidgetProvider";
    public myAppWidgetProvider(){
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
        Log.d(TAG, "onReceive");
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate");
        for(int i=0;i<appWidgetIds.length;i++){
            int appWidgetId = appWidgetIds[i];
            Log.i("oneTableWidget", "onUpdate appWidgetId=" + appWidgetId);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            intent.setClass(context, MainActivity.class);
            // 创建PendingIntent.
            //点击控件时打开应用
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.test, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisable");
    }

    //第一次创建AppWidget时执行
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
