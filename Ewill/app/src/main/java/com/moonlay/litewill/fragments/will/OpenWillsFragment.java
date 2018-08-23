package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.OpenWillActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.adapter.MyWillsAdapter;
import com.moonlay.litewill.adapter.MyWillsItemListener;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.MyWillDetail;
import com.moonlay.litewill.model.MyWillResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 * OpenWillActivity
 */
public class OpenWillsFragment extends BaseFragment {
    private static String TAG = "mydebug_openwill";
    public ArrayList<MyWillDetail> openWills;

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    MyWillsAdapter adapter;
    RelativeLayout layDoc;
    FloatingActionButton fab;

    public OpenWillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_will, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = mView.findViewById(R.id.recycler_view);

        getActivity().setTitle("Open Will");
        ((OpenWillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        openWills = new ArrayList<>();
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        adapter = new MyWillsAdapter(openWills);
        sharedPrefManager = new SharedPrefManager(getContext());

        requestOpenWills();
    }

    private void requestOpenWills() {
        Log.d(TAG, "request open wills");

        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();
        String userEmail = sharedPrefManager.getUserEmail();

        Call<MyWillResponse> call = apiInterface.sharedWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, userEmail);
        call.enqueue(new Callback<MyWillResponse>() {
            @Override
            public void onResponse(Call<MyWillResponse> call, Response<MyWillResponse> response) {
                Log.d(TAG, "API Response: Open Will. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                openWills.addAll(response.body().getResult());
                getOpenWillList();
            }

            @Override
            public void onFailure(Call<MyWillResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void getOpenWillList() {
        Log.d(TAG, "get Open Will List");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillsItemListener(getContext(), recyclerView,
                new MyWillsItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        MyWillDetail myWillDet = openWills.get(position);
                        Log.d(TAG, "data clicked: " + myWillDet.getId() + " " + myWillDet.getName());

                        int willId = myWillDet.getId();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_will, new OpenWillDetailFragment().newInstance(willId));
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
