package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.WillNotifyAdapter;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.EmailReceivers;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.WillNotify;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WillNotifyFragment extends BaseFragment {
    private static String TAG = "mydebug_updnotif";
    ArrayList<Shared> shareds = new ArrayList<>();
    ArrayList<EmailReceivers> emailReceivers = new ArrayList<>();
    String flag;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    Button btnUpdate;
    WillNotifyAdapter adapter;

    public WillNotifyFragment() {
        // Required empty public constructor
    }

    public static WillNotifyFragment newInstance(ArrayList<Shared> shareds, String flag) {
        WillNotifyFragment frag = new WillNotifyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shareds", shareds);
        bundle.putString("flag", flag);
        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upd_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = mView.findViewById(R.id.recycler_view);
        btnUpdate = mView.findViewById(R.id.btn_next);

        init();
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        shareds = (ArrayList<Shared>) getArguments().getSerializable("shareds");
        flag = getArguments().getString("flag");

        /*for (int i = 0; i < shareds.size(); i++) {
            String email = shareds.get(i).getEmail().toString();
            emailReceivers.add(new EmailReceivers(email));
        }*/

        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        adapter = new WillNotifyAdapter(shareds);

        notificationEmail();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWillNotify();
            }
        });
    }

    private void notificationEmail() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void requestWillNotify() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();
        //adapter.sharedEmail

        for (int i = 0; i < adapter.sharedEmail.size(); i++) {
            //String email = shareds.get(i).getEmail().toString();
            String email = adapter.sharedEmail.get(i).toString();
            emailReceivers.add(new EmailReceivers(email));
        }

        Call<StandardResponse> call = apiInterface.willNotify(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new WillNotify(emailReceivers, flag));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Will notify: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                Log.d(TAG, "will notify size: " + emailReceivers.size());
                for (int i = 0; i < emailReceivers.size(); i++) {
                    Log.d(TAG, "will " + i + ": " + shareds.get(i).getEmail());
                }

                if (response.code() == 200 && response.isSuccessful()) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }
}
