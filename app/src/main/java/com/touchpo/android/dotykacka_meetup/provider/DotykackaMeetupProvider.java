package com.touchpo.android.dotykacka_meetup.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.touchpo.android.dotykacka_meetup.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

import static android.provider.BaseColumns._ID;
import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.TAB_NAME;

/**
 * @author kocopepo
 *         created on 02.05.16
 */
public class DotykackaMeetupProvider extends ContentProvider {

    public static final String TAG = DotykackaMeetupProvider.class.getSimpleName();
    public static final String AUTHORITY = "com.touchpo.android.dotykacka_meetup.provider";

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URI_EMPLOYEE_LIST = 1;
    private static final int URI_EMPLOYEE_ITEM = 2;

    private DatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {

        // Initialize your provider. The Android system calls this method immediately after it creates your provider.
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table employee to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI(AUTHORITY, TAB_NAME, URI_EMPLOYEE_LIST);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.touchpo.android.dotykacka_meetup.provider/employee/3" matches, but
         * "content://com.touchpo.android.dotykacka_meetup.provider/employee doesn't.
         */
        sUriMatcher.addURI(AUTHORITY, TAB_NAME + "/#", URI_EMPLOYEE_ITEM);

        mDbHelper = new DatabaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Return Cursor for specified data
        SQLiteDatabase readableDb = mDbHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case URI_EMPLOYEE_LIST:
                cursor = readableDb.query(TAB_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_EMPLOYEE_ITEM:
                StringBuilder selectionSb = new StringBuilder(selection);
                ArrayList<String> selectionArgsArray;
                if (selectionArgs == null) {
                    selectionArgsArray = new ArrayList<>();
                } else {
                    selectionArgsArray = new ArrayList<>(Arrays.asList(selectionArgs));
                }

                if (selectionSb.length() > 0) {
                    selectionSb.append(" AND ");
                }
                selectionSb.append(_ID);
                selectionSb.append(" = ?");
                selectionArgsArray.add(uri.getLastPathSegment());
                selection = selectionSb.toString();
                selectionArgs = selectionArgsArray.toArray(new String[selectionArgsArray.size()]);
                cursor = readableDb.query(TAB_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case URI_EMPLOYEE_LIST:
                /* This method should return the content URI for the new row. */
                SQLiteDatabase writableDb = mDbHelper.getWritableDatabase();
                long rowId = writableDb.insert(TAB_NAME, "", values);
                if (rowId == 0) {
                    throw new SQLiteException("Cannot insert values into " + TAB_NAME);
                }
                Uri rowIdUri = ContentUris.withAppendedId(uri, rowId);
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(rowIdUri, null);
                }
                return rowIdUri;
            default:
                throw new IllegalArgumentException("Invalid URL");
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
        int count = 0;
        SQLiteDatabase writableDb = mDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case URI_EMPLOYEE_LIST:
                count = writableDb.update(TAB_NAME, values, whereClause, whereArgs);
                break;
            case URI_EMPLOYEE_ITEM:
                StringBuilder sbWhere = new StringBuilder(whereClause != null ? whereClause : "");
                if (sbWhere.length() > 0) {
                    sbWhere.append(" AND ");
                }
                sbWhere.append(_ID)
                        .append(" = ")
                        .append(uri.getLastPathSegment());
                whereClause = sbWhere.toString();
                count = writableDb.update(TAB_NAME, values, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid URL");
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, String whereClause, String[] whereArgs) {
        int count = 0;
        SQLiteDatabase writableDb = mDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case URI_EMPLOYEE_LIST:
                count = writableDb.delete(TAB_NAME, whereClause, whereArgs);
                break;
            case URI_EMPLOYEE_ITEM:
                StringBuilder sbWhere = new StringBuilder(whereClause != null ? whereClause : "");
                if (sbWhere.length() > 0) {
                    sbWhere.append(" AND ");
                }
                sbWhere.append(_ID)
                        .append(" = ")
                        .append(uri.getLastPathSegment());
                whereClause = sbWhere.toString();
                count = writableDb.delete(TAB_NAME, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid URL");
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Return the MIME type corresponding to a content URI.

        switch (sUriMatcher.match(uri)) {
            case 1:
                return "vnd." + AUTHORITY + ".dir/vnd." + AUTHORITY+ "." + TAB_NAME;
            case 2:
                return "vnd." + AUTHORITY + ".item/vnd." + AUTHORITY+ "." + TAB_NAME;
            default:
                return null;
        }
    }
}
