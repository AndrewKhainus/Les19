package com.radomar.les19.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "db_name.db";
    private static final int DATABASE_VERSION = 1;

    public static final String NOTIFICATIONS_TABLE_NAME = "notifications";
    public static final String COL_TITLE = "title";
    public static final String COL_SUBTITLE = "subTitle";
    public static final String COL_MESSAGE = "message";

    private static final String NOTIFICATIONS_TABLE_CRETE =
            "CREATE TABLE " + NOTIFICATIONS_TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TITLE + " TEXT, " +
                    COL_SUBTITLE + " TEXT, " +
                    COL_MESSAGE + " TEXT);";

    private SQLiteDatabase mDataBase;

    public DatabaseHelper(Context _context) {
        super(_context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _sqLiteDatabase) {
        _sqLiteDatabase.execSQL(NOTIFICATIONS_TABLE_CRETE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int _i, int _i1) {

    }

    public void saveNotification(String _title, String _subTitle, String _message) {
        mDataBase = getWritableDatabase();

        String insert = "INSERT INTO " + DatabaseHelper.NOTIFICATIONS_TABLE_NAME + " (" +
                DatabaseHelper.COL_TITLE + ", " +
                DatabaseHelper.COL_SUBTITLE + ", " +
                DatabaseHelper.COL_MESSAGE + ") VALUES (" +
                "'" + _title + "', '" + _subTitle + "', '" + _message + "')";

        mDataBase.execSQL(insert);
        mDataBase.close();
    }

    public void dropTable() {
        mDataBase = getWritableDatabase();
        mDataBase.execSQL("DELETE FROM "+ NOTIFICATIONS_TABLE_NAME);
        mDataBase.close();
    }


}