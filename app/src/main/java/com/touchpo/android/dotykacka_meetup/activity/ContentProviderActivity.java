package com.touchpo.android.dotykacka_meetup.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.touchpo.android.dotykacka_meetup.R;
import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee;
import com.touchpo.android.dotykacka_meetup.provider.DotykackaMeetupContract.Employee.EmployeeColumns;

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

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(Employee.CONTENT_URI), null, null, null, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            mEmployeeName.setText(cursor.getString(cursor.getColumnIndex(EmployeeColumns.COL_EMPLOYEE)));
            mEmployeeRole.setText(cursor.getString(cursor.getColumnIndex(EmployeeColumns.COL_ROLE)));
        }
    }
}
