package dk.rus_1_katrinebjerg.barapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Tutor extends BaseModel{
    public static final String TABLE_NAME = "tutor";

    public int id;
    public String name;
    public String streetName;
    public double spendAmount;

    @Override
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public void populateFromCursor(Cursor cursor) {

    }

    public static void createTable(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT" +
                "streetName TEXT" +
                "spendAmount REAL" +
                ")";

        db.execSQL(CREATE_WEATHER_TABLE);
    }
}
