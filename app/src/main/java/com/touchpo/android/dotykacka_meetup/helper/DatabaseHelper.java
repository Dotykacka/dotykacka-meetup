package com.touchpo.android.dotykacka_meetup.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee;
import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns;

/**
 * @author kocopepo
 *         created on 08.05.16
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DB_NAME = "DotykackaMeetUp";
    public static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_EMPLOYEE =
            "CREATE TABLE " + Employee.TAB_NAME + " (" +
                    EmployeeColumns._ID + " INTEGER PRIMARY KEY," +
                    EmployeeColumns.COL_EMPLOYEE + TEXT_TYPE + COMMA_SEP +
                    EmployeeColumns.COL_ROLE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_EMPLOYEE =
            "DROP TABLE IF EXISTS " + Employee.TAB_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade policy is to simply to discard the data and start over
        db.execSQL(SQL_DELETE_EMPLOYEE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
