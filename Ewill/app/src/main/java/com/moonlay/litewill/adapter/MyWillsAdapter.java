package com.moonlay.litewill.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.model.MyWillDetail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by nandana.samudera on 7/2/2018.
 */

public class MyWillsAdapter extends RecyclerView.Adapter<MyWillsAdapter.ViewHolder> {

    private List<MyWillDetail> myWills;

    public MyWillsAdapter(List<MyWillDetail> myWills) {
        this.myWills = myWills;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_will_mywill, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyWillDetail myWillDetail = myWills.get(position);
        String createdDateRaw = myWillDetail.getCreatedUtc().toString();
        Date myDate;
        String newFormat = "dd MMM yyyy";
        String newDateString = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            myDate = simpleDateFormat.parse(createdDateRaw);
            simpleDateFormat.applyPattern(newFormat);
            newDateString  = simpleDateFormat.format(myDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvWillName.setText(myWillDetail.getName());
        holder.tvCreatedDate.setText(newDateString);
    }

    @Override
    public int getItemCount() {
        return myWills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWillName, tvCreatedDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWillName = itemView.findViewById(R.id.tv_will_name);
            tvCreatedDate = itemView.findViewById(R.id.tv_created_date_data);
        }
    }
}
