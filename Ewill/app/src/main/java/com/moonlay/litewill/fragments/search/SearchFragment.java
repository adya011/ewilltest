package com.moonlay.litewill.fragments.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.SearchActivity;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.MyWillsItemListener;
import com.moonlay.litewill.adapter.SearchAdapter;
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
 */
public class SearchFragment extends BaseFragment {
    public static String TAG = "mydebug_search";
    public ArrayList<MyWillDetail> searchResult = new ArrayList<>();

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    SearchAdapter adapter;
    EditText etSearchBar;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment frag = new SearchFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString();
        frag.setArguments(bundle)*/

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        adapter = new SearchAdapter(searchResult);

        recyclerView = mView.findViewById(R.id.recycler_view);
        etSearchBar = mView.findViewById(R.id.sch_search_bar);
        etSearchBar.requestFocus();

        ((SearchActivity) getActivity()).setActionBarTitle("Search Will");
        ((SearchActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        etSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d(TAG, "get search");
                    if (etSearchBar.getText() != null) {
                        requestSearchResult();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void getData(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyWillsItemListener(getContext(), recyclerView,
                new MyWillsItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        MyWillDetail myWillDet = searchResult.get(position);
                        Log.d(TAG, "data clicked: " + myWillDet.getId() + " " + myWillDet.getName());

                        int willId = myWillDet.getId();

                        Intent intent = new Intent(getActivity(), WillActivity.class);
                        intent.putExtra("will_intent", "go_to_will_detail");
                        intent.putExtra("will_det_id", willId);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private void requestSearchResult() {
        final String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();
        final String searchInput = etSearchBar.getText().toString();
        final int memberType = 3;

        Call<MyWillResponse> call = apiInterface.searchWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                searchInput, memberType);
        call.enqueue(new Callback<MyWillResponse>() {
            @Override
            public void onResponse(Call<MyWillResponse> call, Response<MyWillResponse> response) {
                Log.d(TAG, "API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                searchResult.addAll(response.body().getResult());
                if (searchResult.size() > 0) {
                    Log.d(TAG, "search input: " + searchInput + ", memberType:" + memberType);
                    Log.d(TAG, "search 0: " + searchResult.get(0).getFullName());
                    getData();
                }
            }

            @Override
            public void onFailure(Call<MyWillResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
            }
        });
    }
}
