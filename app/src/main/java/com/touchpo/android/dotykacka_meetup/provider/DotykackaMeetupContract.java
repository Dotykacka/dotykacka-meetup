package com.touchpo.android.dotykacka_meetup.provider;

import android.provider.BaseColumns;

import com.touchpo.android.dotykacka_meetup.helper.DatabaseHelper;

/**
 * @author kocopepo
 *         created on 02.05.16
 */
public final class DotykackaMeetupContract {

    public static final String AUTHORITY = DotykackaMeetupProvider.AUTHORITY;
    public static final String DB_NAME = DatabaseHelper.DB_NAME;

    public static final class Employee {

        public static final String TAB_NAME = "employee";
        public static final String CONTENT_URI = "content://" + AUTHORITY + "/" + TAB_NAME;

        public static final class EmployeeColumns implements BaseColumns {
            public static final String COL_EMPLOYEE = "employee";
            public static final String COL_ROLE = "role";

            public static String[] getAll() {
                return new String[]{_ID, COL_EMPLOYEE, COL_ROLE};
            }
        }
    }
}
