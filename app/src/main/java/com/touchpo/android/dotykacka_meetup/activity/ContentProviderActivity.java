package com.touchpo.android.dotykacka_meetup.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.touchpo.android.dotykacka_meetup.R;
import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends AppCompatActivity {

    @BindView(android.R.id.text1)
    TextView mEmployeeName;
    @BindView(android.R.id.text2)
    TextView mEmployeeRole;
    @BindView(R.id.employee_name)
    EditText mNewEmployeeName;
    @BindView(R.id.employee_role)
    EditText mNewEmployeeRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);

        /* Instead of AsyncTask you can use CursorLoader within LoaderManager */
        LoadEmployeesTask loadTask = new LoadEmployeesTask();
        loadTask.execute();
    }

    @OnClick(R.id.employee_add)
    public void onEmployeeAddClick() {
        String name = mNewEmployeeName.getText().toString();
        String role = mNewEmployeeRole.getText().toString();
        ContentValues values = new ContentValues();
        values.put(Employee.EmployeeColumns.COL_EMPLOYEE, name);
        values.put(Employee.EmployeeColumns.COL_ROLE, role);
        InsertEmployeeTask task = new InsertEmployeeTask(values);
        task.execute();
    }

    private class LoadEmployeesTask extends AsyncTask<Void, Void, Cursor> {

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

    private class InsertEmployeeTask extends AsyncTask<Void, Void, Uri> {

        private ContentValues mValues;

        public InsertEmployeeTask(ContentValues values) {
            mValues = values;
        }

        @Override
        protected Uri doInBackground(Void... params) {
            /* You must not load cursor in Main UI thread!! */

            ContentResolver resolver = getContentResolver();
            return resolver.insert(Uri.parse(Employee.CONTENT_URI), mValues);
        }

        @Override
        protected void onPostExecute(Uri uri) {
            super.onPostExecute(uri);
            Toast.makeText(ContentProviderActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
