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

import com.moonlay.litewill.OpenWillActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.MyWillDetailDocAdapter;
import com.moonlay.litewill.adapter.UpdWillDetailDocItemListener;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Document;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillDetailDocFragment extends BaseFragment {
    Button btnBack;
    RecyclerView recyclerView;
    MyWillDetailDocAdapter adapter;

    private static String TAG = "mydebug_upddoc";
    ArrayList<Document> documents = new ArrayList<>();

    public UpdWillDetailDocFragment() {
        // Required empty public constructor
    }

    public static UpdWillDetailDocFragment newInstance(ArrayList<Document> documents) {
        UpdWillDetailDocFragment frag = new UpdWillDetailDocFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("documents", documents);
        frag.setArguments(bundle);

        Log.d(TAG, "++ UpdateWillDocument ++");

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "on create");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_will_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = mView.findViewById(R.id.btn_back);

        recyclerView = mView.findViewById(R.id.recycler_view);

        getActivity().setTitle("Will Document");
        init();
    }

    private void init() {
        documents = (ArrayList<Document>) getArguments().getSerializable("documents");
        adapter = new MyWillDetailDocAdapter(documents, R.layout.list_will_update_doc);

        getDocList();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
    }

    private void getDocList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new UpdWillDetailDocItemListener(getContext(), recyclerView,
                new UpdWillDetailDocItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Document document = documents.get(position);
                        String docRemark = document.getDocumentRemark();

                        nextFrag(docRemark);

                        Log.d(TAG, "clicked " + docRemark);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        Log.d(TAG, "document size: " + documents.size());
    }

    private void nextFrag(String imgName){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_will, UpdWillDetailDocViewFragment.newInstace(imgName));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "on destroyed");
        documents.clear();
        super.onDestroy();
    }
}
