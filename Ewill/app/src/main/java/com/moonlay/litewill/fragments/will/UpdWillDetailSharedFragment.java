package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.moonlay.litewill.adapter.MyWillDetailSharedAdapter;
import com.moonlay.litewill.adapter.MyWillDetailSharedItemListener;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.dialogfragment.UpdAddSharedDialog;
import com.moonlay.litewill.dialogfragment.UpdEditSharedDialog;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillDetailSharedFragment extends BaseFragment {
    Button btnNext, btnAddShared;
    RecyclerView recyclerView;

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    MyWillDetailSharedAdapter adapter;

    int willId;

    private static String TAG = "mydebug_updshr";
    public ArrayList<Shared> shareds = new ArrayList<>();

    public UpdWillDetailSharedFragment() {
        // Required empty public constructor
    }

    public static UpdWillDetailSharedFragment newInstance(int willId, ArrayList<Shared> shareds) {
        UpdWillDetailSharedFragment frag = new UpdWillDetailSharedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", willId);
        bundle.putSerializable("shareds", shareds);
        frag.setArguments(bundle);

        Log.d(TAG, "++ UpdateWillShared ++");

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regw4_notify, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareds.clear();

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        recyclerView = mView.findViewById(R.id.recycler_view);

        btnNext = mView.findViewById(R.id.btn_next);

        btnAddShared = mView.findViewById(R.id.btn_notify);

        ((WillActivity) getActivity()).setActionBarTitle("Shared Will");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        shareds = (ArrayList<Shared>) getArguments().getSerializable("shareds");
        adapter = new MyWillDetailSharedAdapter(shareds);
        willId = getArguments().getInt("will_id");

        Log.d(TAG, "-- init --");
        /*if (shareds != null) {
            Log.d(TAG, "Shared 0 email: " + shareds.get(0).getEmail());
        }*/

        getWillShareds();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getFragmentManager();
                fm.popBackStack();*/

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, WillNotifyFragment.newInstance(shareds, Constants.WILL_UPDATED));
                ft.commit();
            }
        });

        btnAddShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNotifyPeopleDialog();
            }
        });
    }

    private void getWillShareds() {
        /*if (shareds != null) {
            Log.d(TAG, "Shared 0 email: " + shareds.get(0).getEmail());
        }*/

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillDetailSharedItemListener(getContext(), recyclerView,
                new MyWillDetailSharedItemListener.NotifyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Shared sharedsDet = shareds.get(position);

                        int sharedId = sharedsDet.getId();
                        String sharedEmail = sharedsDet.getEmail();
                        boolean sharedDoc = sharedsDet.isDocShared();
                        boolean sharedLoc = sharedsDet.isLocShared();

                        Log.d(TAG, "shared item id selected: " + sharedId);

                        showEditNotifyPeopleDialog(position, sharedId, sharedEmail, sharedDoc, sharedLoc);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private void showEditNotifyPeopleDialog(int position, int sharedId, String sharedEmail, boolean sharedDoc, boolean sharedLoc) {
        FragmentManager fm = getFragmentManager();
        UpdEditSharedDialog notifFrag = UpdEditSharedDialog.newInstance(position, sharedId, sharedEmail, sharedDoc, sharedLoc);
        notifFrag.setTargetFragment(UpdWillDetailSharedFragment.this, 1337);
        notifFrag.show(fm, null);
    }

    private void showAddNotifyPeopleDialog() {
        FragmentManager fm = getFragmentManager();
        UpdAddSharedDialog notifFrag = new UpdAddSharedDialog().newInstance(willId);
        notifFrag.setTargetFragment(UpdWillDetailSharedFragment.this, 1337);
        notifFrag.show(fm, null);
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void refreshData() {
        getWillShareds();
    }

    public void updateDataEdit(int index, String emailUpd, boolean docShared, boolean locShared) {
        shareds.get(index).setEmail(emailUpd);
        shareds.get(index).setDocShared(docShared);
        shareds.get(index).setLocShared(locShared);
    }

    public void updateDataDelete(int index) {
        shareds.remove(index);
    }

    public void updateDataAdd(String emailUpd, boolean docShared, boolean locShared) {
        shareds.add(new Shared(emailUpd, docShared, locShared));
    }
}
