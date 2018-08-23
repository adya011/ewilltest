package com.moonlay.litewill.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.model.NotificationDb;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private List<NotificationDb> notifications;

    public NotificationsAdapter(List<NotificationDb> notifications) {
        this.notifications = notifications;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        NotificationDb notificationDb = notifications.get(position);
        String creatorName = notificationDb.getCreator_name();
        String flag = notificationDb.getFlag();
        String notifText;

        switch (flag) {
            case Constants.WILL_UPDATED:
                notifText = creatorName + " has updated a will that was shared with you";
                break;

            case Constants.WILL_DELETED:
                notifText = creatorName + " has deleted a will that was shared with you";
                break;

            case Constants.WILL_NOWILL:
                notifText = creatorName + " has no will";
                break;

            default:
                notifText = "Creator: " + creatorName + ". Notification text here";
                break;
        }

        holder.tvNotification.setText(notifText);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNotification;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotification = itemView.findViewById(R.id.tv_notification);
        }
    }
}
