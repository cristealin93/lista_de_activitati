package com.lista.listdeactiviti.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="ActivityList.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME="my_activity";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="activity_title";

     public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+
                " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_TITLE+" TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public void addNewActivity(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE,title);

        long result=db.insert(TABLE_NAME,null,cv);

        if(result==-1){
            Toast.makeText(context, "Eroare!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Activitate nouă adăugată cu succes!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateData(String row_id,String title){
         SQLiteDatabase db=this.getWritableDatabase();
         ContentValues cv=new ContentValues();
         cv.put(COLUMN_TITLE,title);
        long result=db.update(TABLE_NAME,cv,"_id=?",new String[] {row_id});

        if (result == -1) {

            Toast.makeText(context, "Eroare", Toast.LENGTH_SHORT).show();
            
        }else {
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteData(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result= db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Eroare", Toast.LENGTH_SHORT).show();
    }else {
            Toast.makeText(context, "Activitatea a fost stearsa.", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteAllData() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
}
