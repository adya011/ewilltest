package com.moonlay.litewill.fragments.reg_member_up_searcher;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.SearchActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpgradeSearcher;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpSearcher1Fragment extends BaseFragment {
    private static String TAG = "mydebug_upsearch";

    Button btnUpgrade;
    TextInputEditText etFullName, etAddress;
    AppCompatSpinner spUpgradeTo, spReside;
    ApiInterface apiInterface;

    String fullName, address;
    int memberTypeId;
    boolean isSingaporean;


    public UpSearcher1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_searcher1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFullName = mView.findViewById(R.id.et_fullname);
        etAddress = mView.findViewById(R.id.et_address);
        btnUpgrade = mView.findViewById(R.id.btn_upgrade);
        spUpgradeTo = mView.findViewById(R.id.sp_upgrade);
        spReside = mView.findViewById(R.id.sp_reside);

        init();
    }

    private void init() {
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        UpgradeToAdapter();
        resideAdapter();

        fullName = etFullName.getText().toString();
        address = etAddress.getText().toString();

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpSearcher();
            }
        });
    }

    private void requestUpSearcher() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<StandardResponse> call = apiInterface.upgradeSearcher(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new UpgradeSearcher(fullName, address, memberTypeId, isSingaporean));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Up Searcher: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    sharedPrefManager.setUserMemberType(memberTypeId);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void resideAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, Constants.yesNo);
        spReside.setAdapter(adapter);
        spReside.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        isSingaporean = true;
                        break;
                    case 1:
                        isSingaporean = false;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void UpgradeToAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, Constants.upgradeTo);
        spUpgradeTo.setAdapter(adapter);
        spUpgradeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        memberTypeId = 4;
                        break;
                    case 1:
                        memberTypeId = 2;
                        break;
                    case 2:
                        memberTypeId = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
