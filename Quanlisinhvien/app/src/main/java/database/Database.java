package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
public static final String DB_NAME="studentManager.sqlite";
public static final int DB_VERSION=1;
public static final String TBL_NAME="student";
public static final String COL_Id="Masv";
public static final String COL_NAME="StudentName";
public static final String COL_CLASSNAME="ClassName";
public static final String COL_TOTALSCORE="TotalScore";
public static final String COL_IMAGE="Image";

public Database (@Nullable Context context){
    super(context,DB_NAME,null,DB_VERSION);
}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "
        +TBL_NAME+" ("
        +COL_Id +" INTEGER PRIMARY KEY AUTOINCREMENT, "
        +COL_NAME+" VARCHAR(50) UNIQUE ,"
        +COL_CLASSNAME+" VARCHAR(100) ,"
        +COL_TOTALSCORE+" REAL ,"
        +COL_IMAGE+" BLOB )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    //select
    public Cursor queryData(String sql){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    //insert update delete
    public void execSql(String sql){
        SQLiteDatabase db=getWritableDatabase();
         db.execSQL(sql);
    }
    public boolean insertData(String studentName,String className,double totalScore,byte[]image){
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql="INSERT INTO "+TBL_NAME+ " VALUES (null, ?, ?,?,?) ";
            SQLiteStatement statement=db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1,studentName);
            statement.bindString(2,className);
            statement.bindDouble(3,totalScore);
            statement.bindBlob(4,image);
            statement.execute();
            return  true;
        }catch (Exception e){
            return false;
        }
    }
    public int getNumOfRow(){
    Cursor cursor=queryData("SELECT * FROM "+TBL_NAME );
    int cursorNum=cursor.getCount();
    cursor.close();
    return cursorNum;
    }
    public void CreateDatabase(){
        if(getNumOfRow()==0){
        try {
            execSql("INSERT INTO "+TBL_NAME+" VALUES (null, 'NguyenPhuocHung', 'CNTTK62', 100,null )");
            execSql("INSERT INTO "+TBL_NAME+" VALUES (null, 'ThanhNgocHuy', 'CNTTK62', 90,null )");
            execSql("INSERT INTO "+TBL_NAME+" VALUES (null, 'DoVietTue', 'CNTTK62', 80,null )");
            execSql("INSERT INTO "+TBL_NAME+" VALUES (null, 'NguyenThiThanhNhu', 'CNTTK62', 80,null )");
        }catch (Exception e){
            Log.e("error: ",e.getMessage().toString());
        }
        }
    }

}
