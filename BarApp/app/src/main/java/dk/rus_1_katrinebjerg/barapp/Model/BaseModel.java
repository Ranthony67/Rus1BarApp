package dk.rus_1_katrinebjerg.barapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import dk.rus_1_katrinebjerg.barapp.Helpers.DatabaseHelper;

public abstract class BaseModel {
    public int id;

    public String tableName(){return getClass().getSimpleName().toLowerCase();}

    public abstract ContentValues getContentValues();
    public abstract void populateFromCursor(Cursor cursor);

    public Boolean save(Context context){return DatabaseHelper.getInstance(context).insertModel(this);}
}
