package com.touchpo.android.dotykacka_meetup.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns.COL_EMPLOYEE;
import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns.COL_ID;
import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns.COL_ROLE;
import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns.getAll;
import static com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.TAB_NAME;

/**
 * @author kocopepo
 *         created on 02.05.16
 */
public class DotykackaMeetupProvider extends ContentProvider {

    public static final String TAG = DotykackaMeetupProvider.class.getSimpleName();
    public static final String AUTHORITY = DotykackaMeetupContract.AUTHORITY;

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {

        // TODO: 02.05.16 Initialize your provider. The Android system calls this method immediately after it creates your provider.
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */
            //TODO ADD URIs
        /*
         * Sets the integer value for multiple rows in table employee to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI(AUTHORITY, TAB_NAME, 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.touchpo.android.dotykacka_meetup.provider/employee/3" matches, but
         * "content://com.touchpo.android.dotykacka_meetup.provider/employee doesn't.
         */
        sUriMatcher.addURI(AUTHORITY, TAB_NAME + "/#", 2);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // TODO: 02.05.16 Return Cursor for specified data
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case 1:
                cursor = getMockCursor(2);
                break;
            case 2:
                cursor = getMockCursor(1);
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
    public String getType(Uri uri) {

        // TODO: 02.05.16 Return the MIME type corresponding to a content URI.

        switch (sUriMatcher.match(uri)) {
            case 1:
                return "vnd." + AUTHORITY + ".dir/vnd." + AUTHORITY+ "." + TAB_NAME;
            case 2:
                return "vnd." + AUTHORITY + ".item/vnd." + AUTHORITY+ "." + TAB_NAME;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case 1:
                // TODO: 02.05.16 This method should return the content URI for the new row.
                return ContentUris.withAppendedId(uri, 1L);
            default:
                throw new IllegalArgumentException("Invalid URL");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(TAG, "Access unimplemented method delete()");
        throw new IllegalStateException("Unimplemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "Access unimplemented method update()");
        throw new IllegalStateException("Unimplemented");
    }

    private Cursor getMockCursor(int rows) {
        MatrixCursor cursor = new MatrixCursor(getAll());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (int i = 1; i <= rows; i++) {
                cursor.newRow()
                        .add(COL_ID, i)
                        .add(COL_EMPLOYEE, "Alois Smolik")
                        .add(COL_ROLE, "support specialist");
            }
        }
        return cursor;
    }
}
