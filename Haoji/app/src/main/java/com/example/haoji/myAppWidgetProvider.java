package com.example.haoji;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.haoji.dailyActivity.dailyActivity;

/**
 * Created by HP on 2017/12/13.
 * 桌面控件
 * 定义创建，删除，更新的操作
 * 每次插入一个AppWidget，onReceive和onUpdate都会被执行
 */

public class myAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "myWidgetProvider";
    public static final String ACTION_ITEM_CLICK = "android.appwidget.action.CNTVNEWS_ITEM_CLICK";
    private RemoteViews views;
    private ComponentName mComponentName;
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

            // 创建PendingIntent.
            //点击“今日日程”时打开应用
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, dailyActivity.class), 0);
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.test, pendingIntent);
            //设置listview的adapter
            //通过ListViewService更新桌面控件的listView中的数据
            Intent lvIntent = new Intent(context, ListViewService.class);
            views.setRemoteAdapter(R.id.table_list, lvIntent);
            views.setEmptyView(R.id.table_list,android.R.id.empty);

            // 设置item点击事件的intent模板
            // listview不能像普通的按钮一样通过 setOnClickPendingIntent 设置点击事件
            Intent listViewItemClickIntent = new Intent(ACTION_ITEM_CLICK);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,appWidgetIds[i], listViewItemClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
