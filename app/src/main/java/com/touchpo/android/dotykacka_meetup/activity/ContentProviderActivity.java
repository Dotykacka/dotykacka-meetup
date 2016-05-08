package com.touchpo.android.dotykacka_meetup.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.touchpo.android.dotykacka_meetup.R;
import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentProviderActivity extends AppCompatActivity {

    @BindView(android.R.id.text1)
    TextView mEmployeeName;
    @BindView(android.R.id.text2)
    TextView mEmployeeRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);

        /* Instead of AsyncTask you can use CursorLoader */
        LoadEmployeesTask loadTask = new LoadEmployeesTask(this);
        loadTask.execute();
    }

    private class LoadEmployeesTask extends AsyncTask<Void, Void, Cursor> {

        private Context mContext;

        public LoadEmployeesTask(Context context) {
            mContext = context;
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            /* You must not load cursor in Main UI thread!! */

            ContentResolver resolver = getContentResolver();
            return resolver.query(Uri.parse(Employee.CONTENT_URI), null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
                mEmployeeName.setText(cursor.getString(cursor.getColumnIndex(Employee.EmployeeColumns.COL_EMPLOYEE)));
                mEmployeeRole.setText(cursor.getString(cursor.getColumnIndex(Employee.EmployeeColumns.COL_ROLE)));
            }
            if (cursor != null) {
                /* Don't forget to close the cursor! */
                cursor.close();
            }
        }
    }
}
