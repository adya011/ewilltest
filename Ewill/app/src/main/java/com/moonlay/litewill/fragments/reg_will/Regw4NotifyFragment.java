package com.moonlay.litewill.fragments.reg_will;


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
import com.moonlay.litewill.adapter.MyWillDetailSharedAdapter;
import com.moonlay.litewill.adapter.MyWillDetailSharedItemListener;
import com.moonlay.litewill.dialogfragment.RegAddSharedDialog;
import com.moonlay.litewill.dialogfragment.RegEditSharedDialog;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.Shared;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Regw4NotifyFragment extends BaseFragment {
    Button btnNext, btnNotify;
    RecyclerView recyclerView;
    MyWillDetailSharedAdapter adapter;

    public ArrayList<Shared> shareds = new ArrayList<>();

    private static String TAG = "mydebug_regwill4";

    public Regw4NotifyFragment() {
        // Required empty public constructor
    }

    public static Regw4NotifyFragment newInstance(String willName, Address[] willAddresses, ArrayList<Document> willDocument) {
        Regw4NotifyFragment frag = new Regw4NotifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("will_name", willName);
        bundle.putSerializable("will_address", willAddresses);
        bundle.putSerializable("will_document", willDocument);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regw4_notify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        btnNotify = mView.findViewById(R.id.btn_notify);
        recyclerView = mView.findViewById(R.id.recycler_view);
        init();
        //sharedData();
    }


    private void init() {
        //adapter = new MyWillDetailSharedAdapter(((RegWillActivity) getActivity()).shareds);
        adapter = new MyWillDetailSharedAdapter(shareds);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillDetailSharedItemListener(getContext(), recyclerView,
                new MyWillDetailSharedItemListener.NotifyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //Shared shared = ((RegWillActivity) getActivity()).shareds.get(position);
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
            public void onClick(View view) {
                String willName = getArguments().getString("will_name");
                Address[] addresses = (Address[]) getArguments().getSerializable("will_address");
                ArrayList<Document> willDocument = (ArrayList<Document>) getArguments().getSerializable("will_document");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, Regw5SettingFragment.newInstance(willName, addresses, willDocument, shareds));
                ft.addToBackStack(null);
                ft.commit();

                try {
                    Log.d(TAG, "-- Go to regw 5 --");
                    Log.d(TAG, "Will name: " + willName);
                    Log.d(TAG, "Addresses size: " + addresses.length);
                    Log.d(TAG, "Addresses 0 street: " + addresses[0].getStreet());
                    /*Log.d(TAG, "Documents size: " + willDocument.size());
                    Log.d(TAG, "Documents 0: " + willDocument.get(0).getDocumentRemark());*/
                    Log.d(TAG, "Shareds size: " + shareds.size());
                    Log.d(TAG, "Shareds 0: " + shareds.get(0).getEmail());

                } catch (Exception e) {
                    Log.d(TAG, "Exception " + e);
                }

            }
        });
    }

    private void showNotifyPeopleAddDialog() {
        FragmentManager fm = getFragmentManager();
        RegAddSharedDialog notifFrag = new RegAddSharedDialog();
        notifFrag.setTargetFragment(Regw4NotifyFragment.this, 1337);
        notifFrag.show(fm, null);
    }

    private void showNotifyPeopleEditDialog(int position, String sharedEmail, boolean sharedLoc, boolean sharedDoc) {
        FragmentManager fm = getFragmentManager();
        RegEditSharedDialog notifFrag = RegEditSharedDialog.newInstance(position, sharedEmail, sharedLoc, sharedDoc);
        notifFrag.setTargetFragment(Regw4NotifyFragment.this, 1337);
        notifFrag.show(fm, null);
    }


    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void refreshAddAdapter() {
        adapter.notifyItemInserted(1);
    }
}
