package com.example.suh.parking;

import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Message;
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
    public static final String CLICK_ACTION = "com.example.android.AppWidget.CLICK_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.AppWidget.EXTRA_ITEM";
    public static final String STRING_ITEM = "com.example.android.AppWidget.STRING_ITEM";
    public static final String START_DIALOG = "com.example.android.AppWidget.START_DIALOG";

    public static String member = "";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        dataDB nick_db = new dataDB(context.getApplicationContext(),"POSITION.db",null,1);
        String getmem = member;

        //listview를 widget에 올리고 수정할 수 있는 dialog activity 만들기
        Intent intent = new Intent();

//        Log.e("dd",nickname);

       // Toast.makeText(context, nickname, Toast.LENGTH_SHORT).show();
        String widgetText = nick_db.read(getmem);  // 여기를 listview onclick 한걸로 바꿔주면됨
        if(widgetText.equals("")){
            widgetText="차량의 주차 위치를 추가해주세요";
            views.setTextViewText(R.id.widget_position, widgetText);
        }else{
            String[] List = widgetText.split("/");
            String result ="";
            int i=0;
            for(String item :List){
                result += item;
                if(i==0){
                    result +=" 차는\n";
                }
                if(i==2){
                    result+="층 ";
                }
                if(i==3){
                    result+="-";
                }
                if(i==4){
                    result+="에 있습니다";
                }
                i++;
            }
            views.setTextViewText(R.id.widget_position,result);
        }





        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_position);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context.getApplicationContext(),AppWidget.class.getName());

        int[] widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(widget);

        if(intent.getAction().equals(CLICK_ACTION)){

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            member= intent.getStringExtra(STRING_ITEM);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM,0);
        //    Toast.makeText(context , "Touched view"+viewIndex+ member,Toast.LENGTH_SHORT).show();
            this.onUpdate(context,AppWidgetManager.getInstance(context),widgetIds);

            }

    /*    if(intent.getAction().equals(START_DIALOG)){

            Intent startDialog = new Intent(context, dialog.class);
            startDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startDialog);


        }
*/
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


            Bundle extras = new Bundle();


            Intent itemIntent = new Intent(context,AppWidget.class);
            itemIntent.setAction(AppWidget.CLICK_ACTION);
            itemIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,itemIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setPendingIntentTemplate(R.id.widget_carlist,pendingIntent);


            Intent clickIntent = new Intent(context,dialog.class);
            clickIntent.setAction(AppWidget.START_DIALOG);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.widget_add_position_btn,pendingIntent1);



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
