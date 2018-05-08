package com.example.suh.parking;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new UpdateWidgetListView(this.getApplicationContext(),intent);
    }
}
class UpdateWidgetListView implements RemoteViewsService.RemoteViewsFactory{
    private static int mCount =0;
    private ArrayList<String> mCarList = new ArrayList<String>();
    private Context mContext;
    private int mAppWidgetId;


    public UpdateWidgetListView(Context context, Intent intent) {
        mContext = context;
    //    mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

        dataDB nick_db = new dataDB(mContext.getApplicationContext(),"POSITION.db",null,1);
        dbClass list_db = new dbClass(mContext.getApplicationContext(),"MYLIST.db",null,1);
        String car_list = list_db.read();

        //DB 에서 가져와서 listview에 현재 가지고 있는 모든 차량 리스트를 뽑음

        String[] imsi = car_list.split("/");
        for(String item:imsi){
            mCarList.add(item);
            mCount++;
        }


    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public RemoteViews getViewAt(int position) {



        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(),R.layout.item);
        //여기서 원하는 문자열 처리
        remoteView.setTextViewText(R.id.widget_item,mCarList.get(position));

        //실행시 어떤거 처리 해주는걸 여기서 해야함


            // listview 클릭시 아래에 있는 text 변환 시키기 위한 text
            Bundle extras = new Bundle();

            extras.putInt(AppWidget.EXTRA_ITEM,position);
            extras.putString(AppWidget.STRING_ITEM,mCarList.get(position));

            Intent fillinIntent = new Intent();
            fillinIntent.putExtras(extras);
            remoteView.setOnClickFillInIntent(R.id.widget_item,fillinIntent);




        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
