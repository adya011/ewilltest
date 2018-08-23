package com.moonlay.litewill.fragments.notification;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonlay.litewill.OpenWillActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.adapter.NotificationItemListener;
import com.moonlay.litewill.adapter.NotificationsAdapter;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.will.OpenWillDetailFragment;
import com.moonlay.litewill.model.MyWillDetail;
import com.moonlay.litewill.model.NotificationDb;
import com.moonlay.litewill.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * Dashboard Activity
 */
public class NotificationFragment extends BaseFragment {
    private static String TAG = "mydebug_notification";
    List<NotificationDb> notificationList;

    RecyclerView recyclerView;
    NotificationsAdapter adapter;
    DatabaseHelper db;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = mView.findViewById(R.id.recycler_view);
        init();
    }

    private void init() {
        db = new DatabaseHelper(getContext());
        notificationList = new ArrayList<>();
        adapter = new NotificationsAdapter(notificationList);

        showNotificationsList();
    }

    private void showNotificationsList() {
        try {
            notificationList.addAll(db.getAllNotification());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Exc: " + e);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new NotificationItemListener(getContext(), recyclerView,
                new NotificationItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        NotificationDb notificationDbs = notificationList.get(position);
                        Log.d(TAG, "data clicked: will id: " + notificationDbs.getWill_id());

                        String flag = notificationDbs.getFlag();
                        int willId = notificationDbs.getWill_id();

                        try {
                            if (flag.equals(Constants.WILL_UPDATED))
                                goToWillDetail(willId);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private void goToWillDetail(int willDetailId) {
        Intent intent = new Intent(getContext(), OpenWillActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", willDetailId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
