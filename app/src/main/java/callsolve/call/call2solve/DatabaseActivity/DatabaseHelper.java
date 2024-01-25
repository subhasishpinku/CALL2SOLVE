package callsolve.call.call2solve.DatabaseActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME ="CALL2SOLVE";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAMECUSTOMER ="cuslongin";
    public static final String TABLE_ID ="cusid";
    public static final String TABLE_CUSTOMERID ="customerid";
    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql2 = "CREATE TABLE " + TABLE_NAMECUSTOMER
                + "(" + TABLE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_CUSTOMERID + " VARCHAR);";
                db.execSQL(sql2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS TABLE_NAMELOGIN";
        db.execSQL(sql);

    }
    public boolean addcusId(String customerid){
      SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_CUSTOMERID,customerid);
        db.insert(TABLE_NAMECUSTOMER, null, contentValues);
        db.close();
        return true;
    }

    public boolean updateData(String cusid, String customerid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_ID,cusid);
        contentValues.put(TABLE_CUSTOMERID,customerid);
        db.update(TABLE_NAMECUSTOMER, contentValues, "cusid = ?",new String[] { cusid });
        return true;
    }
     public Cursor getcusId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAMECUSTOMER,null);
        return res;
    }

}
