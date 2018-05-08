package com.example.suh.parking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataDB extends SQLiteOpenHelper{
    public dataDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE POSITION (NICKNAME VARCHAR2(10) REFERENCES MYLIST(NICKNAME) ON DELETE CASCADE,UNDERGROUNDCHECK VARCHAR2(10),FLOORNUM INT , BIGPOSITION VARCHAR2(10),SMALLPOSITION VARCHAR2(10));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String nickName , String underground , int floornum, String bigposition,int smallposition){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO POSITION VALUES('"+nickName+"','"+underground+"','"+floornum+"','"+bigposition+"','"+smallposition+"');");
        db.close();


    }
    public void delete(String nickName){

        SQLiteDatabase db = getWritableDatabase();
        //DB 입력값을 제거
        db.execSQL("DELETE FROM POSITION WHERE NICKNAME = '"+nickName+"';");
        db.close();
    }


    public String read(String nickName){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM POSITION WHERE NICKNAME = '"+nickName+"';",null);
        while(cursor.moveToNext()){
            result += cursor.getString(0) + "/"
                        +cursor.getString(1)+"/"
                        +cursor.getString(2)+"/"   //이거만 숫자임
                    +cursor.getString(3)+"/"
                    +cursor.getString(4)+"/";
        }

        return result;

    }


}
