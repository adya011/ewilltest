package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpdateWill;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillNameFragment extends BaseFragment {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    Button btnUpdate;
    EditText etWillName;

    public static String TAG = "mydebug_updname";
    ArrayList<Shared> shareds = new ArrayList<>();

    public UpdWillNameFragment() {
        // Required empty public constructor
    }

    public static UpdWillNameFragment newInstance(int willId, String willName,
                                                  ArrayList<Shared> shareds) {
        UpdWillNameFragment frag = new UpdWillNameFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("will_id", willId);
        bundle.putString("will_name", willName);
        bundle.putSerializable("shareds", shareds);

        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg17_will_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        etWillName = mView.findViewById(R.id.et_willname);
        btnUpdate = mView.findViewById(R.id.btn_next);

        ((WillActivity) getActivity()).setActionBarTitle("Will Name");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        final int willId = getArguments().getInt("will_id");
        String willNameOld = getArguments().getString("will_name");
        shareds = (ArrayList<Shared>) getArguments().getSerializable("shareds");
        etWillName.setText(willNameOld);
        btnUpdate.setText("Update");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = sharedPrefManager.getLoginToken();
                String uniqueID = UUID.randomUUID().toString();

                String willName = etWillName.getText().toString();

                Call<StandardResponse> call = apiInterface .updateWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                        token, new UpdateWill(willId, willName));
                call.enqueue(new Callback<StandardResponse>() {
                    @Override
                    public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                        Log.d(TAG, "Will Update: API response code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());

                        /*FragmentManager fm = getFragmentManager();
                        fm.popBackStack();*/

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_will, WillNotifyFragment.newInstance(shareds, Constants.WILL_UPDATED));
                        ft.commit();
                    }

                    @Override
                    public void onFailure(Call<StandardResponse> call, Throwable t) {
                        Log.d(TAG, "Failed");
                    }
                });
            }
        });
    }
}
