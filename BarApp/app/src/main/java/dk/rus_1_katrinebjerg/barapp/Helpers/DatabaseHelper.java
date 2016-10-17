package dk.rus_1_katrinebjerg.barapp.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.Model.BaseModel;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "reportingDatabase";
    private static final int DATABASE_VERSION = 12;

    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context context) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BarItem.createTable(db);
        Tutor.createTable(db);
        Log.v("DatabaseHelper", "Created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + BarItem.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Tutor.TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean insertModel(BaseModel model) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            int id = (int) db.insertOrThrow(model.tableName(), null, model.getContentValues());
            db.setTransactionSuccessful();
            model.id = id;

            Log.v("DatabaseHelper", "Inserted model");
        } catch (Exception e) {
            return false;
        } finally {
            db.endTransaction();
        }

        return true;
    }

    public void deleteModel(BaseModel model) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(model.tableName(), "id=" + model.id, null);
            db.setTransactionSuccessful();
            Log.v("DatabaseHelper", "Deleted model successfull");
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public <T extends BaseModel> ArrayList<T> getAll(Class<T> cls) throws Exception {
        return getAll(cls, "");
    }

    public <T extends BaseModel> ArrayList<T> getAll(Class<T> cls, String whereClause) throws Exception {
        T info = cls.newInstance();
        String tableName = info.tableName();

        ArrayList<T> objectList = new ArrayList<>();

        String where =  whereClause.isEmpty() ? "" : " WHERE " + whereClause;
        String sql = String.format("SELECT * FROM %s%s ORDER BY id DESC", tableName, where);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    T object = cls.newInstance();
                    object.populateFromCursor(cursor);
                    objectList.add(object);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.v("DatabaseHelper", "GetAll Exception: " + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return objectList;
    }

    public <T extends BaseModel> T find(Class<T> cls, int id) throws Exception {
        T info = cls.newInstance();
        String tableName = info.tableName();

        String sql = String.format("SELECT * FROM %s WHERE id = %s", tableName, id);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName, null, String.format("id=%s", id), null, null, null, null, null);

        T model = null;

        try {
            cursor.moveToFirst();
            T object = cls.newInstance();
            object.populateFromCursor(cursor);
            model = object;

        } catch (Exception e) {
            Log.v("DatabaseHelper", "Find Exception: " + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return model;
    }

    public Boolean update(BaseModel object) {
        SQLiteDatabase db = getReadableDatabase();
        int affected = db.update(object.tableName(), object.getContentValues(), "id=" + object.id, null);

        if (affected == 1) {
            return true;
        }

        return false;
    }
}
