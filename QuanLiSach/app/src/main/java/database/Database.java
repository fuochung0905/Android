package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.quanlisach.MainActivity;

import java.util.ArrayList;

import model.Sach;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME="sach.sqlite";
    public static final int DB_VERSION=1;
    public static final String TBL_NAME="sach";
    public static final String COL_MA="maSach";
    public static final String COL_TEN="tenSach";
    public static final String COL_GIA="giaSach";


    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+TBL_NAME+" ("
                +COL_MA+" VARCHAR(10) PRIMARY KEY ,"
                +COL_TEN+" VARCHAR(30) ,"
                +COL_GIA+" REAL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    public Cursor querySql(String sql){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    public void execSql(String sql){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);
    }
    private int getNumberOfRow(){
        String sql="SELECT * FROM "+TBL_NAME;
       Cursor cursor=querySql(sql);
       int count =cursor.getCount();
       cursor.close();
       return count;
    }
    public void CreateSampleData(){
        if(getNumberOfRow()==0){
            try{
                execSql("INSERT INTO "+TBL_NAME+" VALUES ('SACH_1','DORAEMON',10000)");
                execSql("INSERT INTO "+TBL_NAME+" VALUES ('SACH_2','ONEPICE',16000)");
                execSql("INSERT INTO "+TBL_NAME+" VALUES ('SACH_3','TRANG QUYNH',8000)");
            }catch (Exception e){
                Log.e("error",e.getMessage().toString());
            }
        }
    }
    public boolean insertData(String maSach,String tenSach,Double giaSach){
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql="INSERT INTO "+TBL_NAME+ " VALUES ( ?,?,?) ";
            SQLiteStatement statement=db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1,maSach);
            statement.bindString(2,tenSach);
            statement.bindDouble(3,giaSach);
            statement.execute();
            return  true;
        }catch (Exception e){
            return false;
        }
    }
}
