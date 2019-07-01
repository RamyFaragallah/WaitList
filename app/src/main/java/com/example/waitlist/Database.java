package com.example.waitlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "sqlitedb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create table waitlist (id INTEGER primary key autoincrement," +
        "guestname TEXT not null,partyorder INTEGER not null,time TIMESTAMP default CURRENT_TIMESTAMP  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists waitlist ");
    onCreate(db);
    }
    public Cursor getallguests(SQLiteDatabase database){
        Cursor c=database.query("waitlist",
                null,
                null,
                null,
                null,
                null,"time"
                );
        return c;

    }
    public long insert(String name,int num,SQLiteDatabase db)
    {
        ContentValues cv=new ContentValues();
        cv.put("guestname",name);
        cv.put("partyorder",num);
        Long i=db.insert("waitlist",null,cv);
        return i;
    }
    public boolean delete(SQLiteDatabase db,int id){
        boolean delete=db.delete("waitlist","id =" + id,null)>0;
        return delete;

    }
}
