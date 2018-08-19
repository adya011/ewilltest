package com.moonlay.litewill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.reg_will.Regw3UploadFragment;
import com.moonlay.litewill.model.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandana.samudera on 6/29/2018.
 */

public class MyWillDetailDocAdapter extends RecyclerView.Adapter<MyWillDetailDocAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Document> documentList;
    int layout;

    public MyWillDetailDocAdapter(ArrayList<Document> documentList, int layout) {
        this.documentList = documentList;
        this.layout = layout;
    }

    public MyWillDetailDocAdapter(ArrayList<Document> documentList, int layout, Context context) {
        this.documentList = documentList;
        this.layout = layout;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Document document = documentList.get(position);
        holder.tvFileName.setText(document.getDocumentRemark());
        if (layout == R.layout.list_will_upload) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    documentList.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFileName;
        public ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            if (layout == R.layout.list_will_upload) {
                btnDelete = itemView.findViewById(R.id.btn_delete);
            }
        }
    }
}
