package com.example.suh.parking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbClass extends SQLiteOpenHelper{
    public dbClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE MYLIST (BRANDNAME VARCHAR2(10), CARMODEL VARCHAR2(10) , NICKNAME VARCHAR2(10));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String brandName, String modelName,String nickName){
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MYLIST VALUES('"+brandName+"','"+modelName+"','"+nickName+"');");
        db.close();

    }

    public void delete(String nickName){

        SQLiteDatabase db = getWritableDatabase();
        //DB 입력값을 제거
        db.execSQL("DELETE FROM MYLIST WHERE NICKNAME = '"+nickName+"';");

        db.close();
    }

    public String read(){

        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT NICKNAME FROM MYLIST",null);
        while(cursor.moveToNext()){
                result += cursor.getString(0) + "/";
        }

        return result;


    }


}


