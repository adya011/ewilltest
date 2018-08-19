package com.moonlay.litewill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.model.Shared;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 6/28/2018.
 */

public class WillNotifyAdapter extends RecyclerView.Adapter<WillNotifyAdapter.ViewHolder> {

    private ArrayList<Shared> sharedList;
    public ArrayList<String> sharedEmail = new ArrayList<>();

    public WillNotifyAdapter(ArrayList<Shared> sharedList) {
        this.sharedList = sharedList;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_update_notify, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shared shared = sharedList.get(position);
        holder.email.setText(shared.getEmail());

        /*String sharedEmail = this.sharedEmail.get(position);
        holder.email.setText(sharedEmail);*/
    }

    @Override
    public int getItemCount() {
        return sharedList.size();
        //return sharedEmail.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email;
        public CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            cb = itemView.findViewById(R.id.cb);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sharedEmail.add(email.getText().toString());
                    } else {
                        sharedEmail.remove(email.getText().toString());
                    }
                }
            });
        }
    }

    public static void changeString(int position) {

    }
}
