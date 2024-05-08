package com.example.databaseapplication53;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseConnection extends SQLiteOpenHelper {
    public DatabaseConnection(Context context) {
        super(context,"StudentData.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE StudentInfo(usn TEXT PRIMARY KEY, name TEXT, branch TEXT, phoneno TEXT, sem INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public Boolean insertValues(String name, String usn, String branch, String phone, int sem){
        SQLiteDatabase myDb= this.getWritableDatabase();
        ContentValues vals=new ContentValues();
        vals.put("usn",usn);
        vals.put("name",name);
        vals.put("branch",branch);
        vals.put("phoneno",phone);
        vals.put("sem",sem);
        long result=myDb.insert("StudentInfo",null,vals);
        return result != -1;
    }
    public Cursor fetchPhoneNo(String studentUSN){
        SQLiteDatabase database=this.getReadableDatabase();
        //database.rawQuery(String.format("select phoneno from StudentData where usn='%s'", studentUSN),null)
        Cursor myPhone= database.rawQuery("select phoneno from StudentInfo where usn='" + studentUSN + "';",null);
        return myPhone;
    }

}
