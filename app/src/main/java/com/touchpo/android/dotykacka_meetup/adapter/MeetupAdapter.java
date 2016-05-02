package com.touchpo.android.dotykacka_meetup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.touchpo.android.dotykacka_meetup.R;
import com.touchpo.android.dotykacka_meetup.activity.ContentProviderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author kocopepo
 *         created on 01.05.16
 */
public class MeetupAdapter extends RecyclerView.Adapter<MeetupAdapter.ViewHolder> {

    private Context mContext;
    private List<MeetupTopic> mList;
    private MeetupItemActionListener mListener;

    public MeetupAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();

        mList.add(new MeetupTopic(
                mContext.getString(R.string.meetup_example_title),
                mContext.getString(R.string.meetup_example_desc),
                null
        ));
        mList.add(new MeetupTopic(
                mContext.getString(R.string.meetup_content_provider_title),
                mContext.getString(R.string.meetup_content_provider_description),
                ContentProviderActivity.class
        ));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meetup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeetupTopic topic = mList.get(position);
        holder.title.setText(topic.title);
        holder.description.setText(topic.shortDescription);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public MeetupTopic getMeetupTopic(int position) {
        return mList.get(position);
    }

    public void setListener(MeetupItemActionListener listener) {
        mListener = listener;
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickListener(getLayoutPosition());
            }
        }
    }

    public static class MeetupTopic {
        public String title;
        public String shortDescription;
        public Class activityClass;

        public MeetupTopic(String title, String description, Class clz) {
            this.title = title;
            shortDescription = description;
            activityClass = clz;
        }
    }

    public interface MeetupItemActionListener{
        void onClickListener(int position);
    }
}
