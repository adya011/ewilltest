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

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.MyWillsAdapter;
import com.moonlay.litewill.adapter.MyWillsItemListener;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.reg_will.Regw1WillNameFragment;
import com.moonlay.litewill.model.MyWillDetail;
import com.moonlay.litewill.model.MyWillResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWillsFragment extends BaseFragment {
    private static String TAG = "mydebug_mywill";

    public List<MyWillDetail> myWills = new ArrayList<>();

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    MyWillsAdapter adapter;
    FloatingActionButton fab;

    public MyWillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_will, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        recyclerView = mView.findViewById(R.id.recycler_view);
        adapter = new MyWillsAdapter(myWills);
        fab = mView.findViewById(R.id.fab);

        ((WillActivity) getActivity()).setActionBarTitle("Wills");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    public void init() {
        final String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Log.d(TAG, "init my wills");

        //refreshAdapter();

        Call<MyWillResponse> call = apiInterface.myWillList(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token);
        call.enqueue(new Callback<MyWillResponse>() {
            @Override
            public void onResponse(Call<MyWillResponse> call, Response<MyWillResponse> response) {
                Log.d(TAG, "API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                myWills.addAll(response.body().getResult());
                getMyWillList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MyWillResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), RegWillActivity.class));
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, new Regw1WillNameFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void getMyWillList() {
        Log.d(TAG, "getMyWillList");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillsItemListener(getContext(), recyclerView,
                new MyWillsItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        MyWillDetail myWillDet = myWills.get(position);
                        Log.d(TAG, "data clicked: " + myWillDet.getId() + " " + myWillDet.getName());

                        int willId = myWillDet.getId();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_will, MyWillDetailFragment.newInstance(willId));
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "wills on resume");
        myWills.clear();
    }
}
