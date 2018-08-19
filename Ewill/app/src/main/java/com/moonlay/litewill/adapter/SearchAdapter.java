package com.moonlay.litewill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.model.MyWillDetail;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 7/2/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<MyWillDetail> searchResult;

    public SearchAdapter(ArrayList<MyWillDetail> searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_search_result, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyWillDetail searchData = searchResult.get(position);
        holder.tvResult.setText(searchData.getFullName());
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvResult = itemView.findViewById(R.id.tv_search_result);
        }
    }
}
