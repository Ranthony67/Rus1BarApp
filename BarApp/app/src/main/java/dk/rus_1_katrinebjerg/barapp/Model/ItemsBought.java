package dk.rus_1_katrinebjerg.barapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemsBought extends BaseModel {
    public static final String TABLE_NAME = "itemsbought";

    public int id;
    public String itemName;
    public int quantity;
    public double singleItemPrice;
    public int tutorId;

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
                "itemName TEXT" +
                "quantity INTEGER" +
                "singleItemPrice REAL" +
                "itemTutorId INTEGER" +
                "FOREIGN KEY (itemTutorId) REFERENCES tutor(tutorId)" +
                ")";

        db.execSQL(CreateTable);
    }
}
