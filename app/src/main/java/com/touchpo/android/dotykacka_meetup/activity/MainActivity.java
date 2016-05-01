package com.touchpo.android.dotykacka_meetup.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.touchpo.android.dotykacka_meetup.R;
import com.touchpo.android.dotykacka_meetup.adapter.MeetupAdapter;
import com.touchpo.android.dotykacka_meetup.adapter.MeetupAdapter.MeetupTopic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MeetupAdapter.MeetupItemActionListener {

    @BindView(android.R.id.list)
    RecyclerView mList;

    private MeetupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        mAdapter = new MeetupAdapter(this);
        mAdapter.setListener(this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onClickListener(int position) {
        MeetupTopic topic =  mAdapter.getMeetupTopic(position);
        startActivity(new Intent(this, topic.activityClass));
    }
}
