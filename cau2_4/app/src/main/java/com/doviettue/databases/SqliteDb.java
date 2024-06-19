package com.doviettue.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

public class SqliteDb extends SQLiteOpenHelper {

    public static String DB_NAME = "product.db";
    public static int DB_VERSION = 1;
    public static String TBL_NAME = "product";
    public static String COL_MASANPHAM = "MaSanPham";
    public static String COL_TENSANPHAM  = "TenSanPham";
    public static String COL_GIASANPHAM = "GiaSanPham";



    public SqliteDb(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + "(" +
                COL_MASANPHAM + " NVARCHAR(250), " +
                COL_TENSANPHAM + " NVARCHAR(250), " +
                COL_GIASANPHAM + " DOUBLE " +
                ") " ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TBL_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insert(String maSp, String tenSP, double giaSP){
        try {
            SQLiteDatabase db = getWritableDatabase(); // writable
            String sql = "INSERT INTO " + TBL_NAME + " VALUES(?,?,?)";
            SQLiteStatement stm = db.compileStatement(sql);
            stm.bindString(1, maSp);
            stm.bindString(2, tenSP);
            stm.bindDouble(3, giaSP);

            stm.executeInsert();
            return true;
        }catch (Exception e){
            Log.e("Loi insert", e.getMessage());
            return false;
        }
    }

    public Cursor select (String sql){
        try {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }catch (Exception e){
            Log.e("Loi select", e.getMessage());
            return null;
        }
    }

    public void sampleData(){
        insert("1", "ten 1", 1);
        insert("2", "ten 12", 12);
        insert("13", "ten 13", 13);
    }
}
