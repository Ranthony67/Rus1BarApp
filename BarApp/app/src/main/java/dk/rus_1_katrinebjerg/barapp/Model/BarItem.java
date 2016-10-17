package dk.rus_1_katrinebjerg.barapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BarItem extends BaseModel{
    public static final String TABLE_NAME = "baritem";

    public int id;
    public String name;
    public double price;

    @Override
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public void populateFromCursor(Cursor cursor) {

    }

    public static void createTable(SQLiteDatabase db) {
        String CreateTable = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT" +
                "price REAL" +
                ")";

        db.execSQL(CreateTable);
    }
}
