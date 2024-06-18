package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME="phoneManager.sqlite";
    public static final int DB_VERSION=1;
    public static final String TBL_NAME="phone";
    public static final String COL_CODE="phoneId";
    public static final String COL_NAME="phoneName";
    public static final String COL_PRICE="price";

public Database(@NonNull Context context){
    super(context,DB_NAME,null,DB_VERSION);
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+TBL_NAME+" ("
                +COL_CODE+" VARCAHR(20) PRIMARY KEY ,"
                +COL_NAME+" VARCHAR(100)  ,"
                +COL_PRICE+" REAL )";
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
    public boolean insertData(String Id,String name,Double price){
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql="INSERT INTO "+TBL_NAME+" VALUES (?,?,?)";
            SQLiteStatement statement=db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(0,Id);
            statement.bindString(1,name);
            statement.bindDouble(2,price);
            statement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private int getNumberOfRow(){
        Cursor cursor=querySql("SELECT * FROM "+TBL_NAME);
        int count =cursor.getCount();
        cursor.close();
        return count;
    }
    public void CreateSampleData(){
        if(getNumberOfRow()==0){
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES('SP_1', 'IPHONE 5S',700000 )");
                execSql("INSERT INTO " + TBL_NAME +" VALUES('SP_2','SAMSUNG GALAXY S4',20000)");
                execSql("INSERT INTO " +TBL_NAME  +" VALUES('SP_3','IPHONE 7S',30000)");
            }catch (Exception e){
                Log.e("error",e.getMessage().toString() );
            }
        }
    }
}
