package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.adapter.MyWillDetailSharedAdapter;
import com.moonlay.litewill.adapter.MyWillDetailSharedItemListener;
import com.moonlay.litewill.adapter.NoWillNotifyAdapter;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.dialogfragment.NoWillNotifyAddSharedDialog;
import com.moonlay.litewill.dialogfragment.NoWillNotifyEditSharedDialog;
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
public class NoWillNotifyFragment extends BaseFragment {
    public static String TAG = "mydebug_nowill";
    Button btnNext, btnNotify;
    RecyclerView recyclerView;
    NoWillNotifyAdapter adapter;
    ApiInterface apiInterface;
    public ArrayList<Shared> shareds = new ArrayList<>();
    ArrayList<EmailReceivers> emailReceivers = new ArrayList<>();

    public NoWillNotifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regw4_notify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        btnNotify = mView.findViewById(R.id.btn_notify);
        recyclerView = mView.findViewById(R.id.recycler_view);

        init();
    }

    private void init() {
        adapter = new NoWillNotifyAdapter(shareds);
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillDetailSharedItemListener(getContext(), recyclerView,
                new MyWillDetailSharedItemListener.NotifyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Shared shared = shareds.get(position);

                        int sharedId = shared.getId();
                        String sharedEmail = shared.getEmail();
                        boolean sharedLoc = shared.isLocShared();
                        boolean sharedDoc = shared.isDocShared();

                        Log.d(TAG, "shared item id selected: " + sharedId);

                        showNotifyPeopleEditDialog(position, sharedEmail, sharedDoc, sharedLoc);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotifyPeopleAddDialog();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNoWillNotify();
            }
        });
    }

    private void requestNoWillNotify() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        for (int i = 0; i < shareds.size(); i++) {
            emailReceivers.add(new EmailReceivers(shareds.get(i).getEmail()));
        }

        Call<StandardResponse> call = apiInterface.willNotify(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new WillNotify(emailReceivers, Constants.WILL_NOWILL));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Will notify: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                Log.d(TAG, "will notify size: " + emailReceivers.size());
                for (int i = 0; i < emailReceivers.size(); i++) {
                    Log.d(TAG, "will " + i + ": " + emailReceivers.get(i).getEmail());
                }

                if (response.code() == 200 && response.isSuccessful()) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {

            }
        });
    }

    private void showNotifyPeopleEditDialog(int position, String sharedEmail, boolean sharedLoc, boolean sharedDoc) {
        FragmentManager fm = getFragmentManager();
        NoWillNotifyEditSharedDialog notifFrag = NoWillNotifyEditSharedDialog.newInstance(position, sharedEmail, sharedLoc, sharedDoc);
        notifFrag.setTargetFragment(NoWillNotifyFragment.this, 1337);
        notifFrag.show(fm, null);
    }

    private void showNotifyPeopleAddDialog() {
        FragmentManager fm = getFragmentManager();
        NoWillNotifyAddSharedDialog notifFrag = new NoWillNotifyAddSharedDialog();
        notifFrag.setTargetFragment(NoWillNotifyFragment.this, 1337);
        notifFrag.show(fm, null);
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void refreshAddAdapter() {
        adapter.notifyItemInserted(1);
    }
}
