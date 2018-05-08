package com.example.suh.parking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        dataDB nick_db = new dataDB(context.getApplicationContext(),"POSITION.db",null,1);


        //listview를 widget에 올리고 수정할 수 있는 dialog activity 만들기
        Intent intent = new Intent();

        String nickname = intent.getStringExtra("row_nickname");
//        Log.e("dd",nickname);

        Toast.makeText(context, nickname, Toast.LENGTH_SHORT).show();
        String widgetText = nick_db.read(nickname);  // 여기를 listview onclick 한걸로 바꿔주면됨
        if(widgetText.equals("")){
            widgetText="추가해주세요";
        }
        views.setTextViewText(R.id.widget_position, widgetText);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_position);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            String appWidgetId = intent.getStringExtra("row_nickname");
            //int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + appWidgetId, Toast.LENGTH_SHORT).show();
        }
            super.onReceive(context,intent);
      //  if(!intent.getAction().isEmpty()){
       //         String car = intent.getStringExtra("row_nickname");
       //     Toast.makeText(context,car+"받아왔수다",Toast.LENGTH_LONG);
       // }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

      //여기 잘 모름
        for(int i =0;i<appWidgetIds.length;++i){
            RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.app_widget);
            Intent intent = new Intent(context,ListWidgetService.class);

            remoteView.setRemoteAdapter(appWidgetIds[i],R.id.widget_carlist,intent);
            //app Widgetids 이게 대체 뭐하는애인지 모르겠다!
            //표시할게 없을때 빈뷰 지정
            //String s = intent.getStringExtra("row_nickname");
          //  Log.e("tag1",s);
            //리스트 아이템 클릭시 실행하는 pending Intent 작성
            //먼지모름
       //     Intent itemIntent = new Intent(Intent.ACTION_VIEW);

            Intent itemIntent = new Intent(context,ListWidgetService.class);
            itemIntent.setAction(AppWidget.TOAST_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,itemIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            remoteView.setPendingIntentTemplate(R.id.widget_carlist,pendingIntent);

       //     String getString = itemIntent.getStringExtra("row_nickname");
       //     Log.e("tag:",getString);
            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteView);
        }
            super.onUpdate(context,appWidgetManager,appWidgetIds);



        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}
