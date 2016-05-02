package com.touchpo.android.dotykacka_meetup.provider;

/**
 * @author kocopepo
 *         created on 02.05.16
 */
public final class MeetupContract {

    public static final String AUTHORITY = "com.touchpo.android.dotykacka_meetup.provider";
    public static final String TAB_EMPLOYEE = "employee";

    public static final class EmployeeColumns {
        public static final String COL_ID = "_ID";
        public static final String COL_EMPLOYEE = "employee";
        public static final String COL_POSITION = "position";

        public static String[] getAll() {
            return new String[]{COL_ID, COL_EMPLOYEE, COL_POSITION};
        }
    }
}
