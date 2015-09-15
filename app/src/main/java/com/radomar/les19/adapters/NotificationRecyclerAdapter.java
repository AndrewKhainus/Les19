package com.radomar.les19.adapters;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radomar.les19.R;
import com.radomar.les19.model.NotificationModel;

import java.util.List;

/**
 * Created by Radomar on 10.09.2015
 */
public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.CustomViewHolder> {

    private final Context mContext;
    private List<NotificationModel> mData;

    public NotificationRecyclerAdapter(Context _context, List<NotificationModel> _data) {
        mContext = _context;
        mData = _data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup _viewGroup, int _viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.row_layout, _viewGroup, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder _customViewHolder, int _i) {
        _customViewHolder.onBind();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     View Holder
     */
    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subTitle;
        TextView message;

        public CustomViewHolder(View _itemView) {
            super(_itemView);

            findViews(_itemView);
            _itemView.setOnClickListener(this);
        }

        public void onBind() {
            NotificationModel notification = mData.get(getAdapterPosition());

            title.setText("title:          " + notification.title);
            subTitle.setText("subtitle:    " + notification.subTitle);
            message.setText("message: " + notification.message);
        }

        private void findViews(View _view) {
            title    = (TextView)  _view.findViewById(R.id.tvTitle_RL);
            subTitle = (TextView)  _view.findViewById(R.id.tvSubTitle_RL);
            message  = (TextView)  _view.findViewById(R.id.tvMessage_RL);

        }

        @Override
        public void onClick(View _v) {
            showNotification();
        }

        private void showNotification() {
            String message = this.message.getText().toString();
            String title = this.title.getText().toString();
            String subtitle = this.subTitle.getText().toString();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setContentTitle(title)
                    .setSubText(subtitle)
                    .setContentText(message)
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1234, builder.build());
        }
    }
}
