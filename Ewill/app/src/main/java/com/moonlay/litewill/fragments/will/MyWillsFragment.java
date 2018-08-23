package com.moonlay.litewill.fragments.will;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.MyWillsAdapter;
import com.moonlay.litewill.adapter.MyWillsItemListener;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.reg_will.Regw1WillNameFragment;
import com.moonlay.litewill.model.MemberInfoResponse;
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
    Button btnCreate;

    int memberMaxWill;


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
        btnCreate = mView.findViewById(R.id.btn_create);

        ((WillActivity) getActivity()).setActionBarTitle("Wills");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    public void init() {
        //btnCreate.setVisibility(View.INVISIBLE);
        testNotificationCount();

        getUserMaxWill();
        requestMyWills();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, new Regw1WillNameFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void testNotificationCount(){
        sharedPrefManager.addNotificationCount(2);

        /*LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = inflater.inflate(R.layout.activity_dashboard, null);
        TextView tv = vi.findViewById()*/

        /*LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.activity_dashboard, null);*/

        //((DashboardActivity)getActivity()).findViewById(R.id.recycler_view);
    }

    private void getUserMaxWill() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<MemberInfoResponse> call = apiInterface.memberInfo(uniqueID, Constants.HEADER_AGENT,
                Constants.HEADER_VERSION, token);
        call.enqueue(new Callback<MemberInfoResponse>() {
            @Override
            public void onResponse(Call<MemberInfoResponse> call, Response<MemberInfoResponse> response) {
                Log.d(TAG, "Get Member Info API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                memberMaxWill = response.body().getResult().get(0).getSubscriptions().get(0).getMaxWillAllowed();
                Log.d(TAG, "getUserMaxWill: " + memberMaxWill);
            }

            @Override
            public void onFailure(Call<MemberInfoResponse> call, Throwable t) {

            }
        });

    }

    private void requestMyWills() {
        final String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<MyWillResponse> call = apiInterface.myWillList(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token);
        call.enqueue(new Callback<MyWillResponse>() {
            @Override
            public void onResponse(Call<MyWillResponse> call, Response<MyWillResponse> response) {
                Log.d(TAG, "API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                myWills.addAll(response.body().getResult());
                getMyWillList();
                Log.d(TAG, "Request My Wills. Member max will: " + memberMaxWill);
                if (myWills.size() < memberMaxWill) {
                    /*btnCreate.setVisibility(View.VISIBLE);*/
                    btnCreate.setEnabled(true);
                } else {
                    /*btnCreate.setVisibility(View.INVISIBLE);*/
                    btnCreate.setEnabled(false);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MyWillResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
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
