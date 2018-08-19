package com.moonlay.litewill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.model.Shared;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 6/28/2018.
 */

public class NoWillNotifyAdapter extends RecyclerView.Adapter<NoWillNotifyAdapter.ViewHolder> {

    public ArrayList<Shared> sharedList;

    public NoWillNotifyAdapter(ArrayList<Shared> sharedList) {
        this.sharedList = sharedList;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_no_will_notify, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shared shared = sharedList.get(position);

        holder.email.setText(shared.getEmail());

        /*if (shared.isLocShared()) {
            holder.location.setText("Yes");
        } else {
            holder.location.setText("No");
        }

        if (shared.isDocShared()) {
            holder.will.setText("Yes");
        } else {
            holder.will.setText("No");
        }*/
    }

    @Override
    public int getItemCount() {
        return sharedList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email, location, will;

        public ViewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            location = itemView.findViewById(R.id.bool_location);
            will = itemView.findViewById(R.id.bool_will);
        }
    }

    public static void changeString(int position){

    }
}
