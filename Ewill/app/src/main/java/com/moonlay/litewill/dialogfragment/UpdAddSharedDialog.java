package com.moonlay.litewill.dialogfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.will.UpdWillDetailSharedFragment;
import com.moonlay.litewill.model.AddShared;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdAddSharedDialog extends DialogFragment {
    public static final String TAG = "mydebug_addshared";

    Button btnSave, btnCancel;
    ImageButton btnDelete;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    TextInputEditText etEmail;
    CheckBox cbLocation, cbWill;

    int willId;

    boolean isWillShared = false, isLocationShared = false;

    public UpdAddSharedDialog() {
        // Required empty public constructor
    }

    public UpdAddSharedDialog newInstance(int willId) {
        UpdAddSharedDialog frag = new UpdAddSharedDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", willId);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify_people, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        btnCancel = view.findViewById(R.id.btn_no);
        btnSave = view.findViewById(R.id.btn_yes);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setVisibility(View.INVISIBLE);
        cbLocation = view.findViewById(R.id.cb_location);
        cbWill = view.findViewById(R.id.cb_will);
        etEmail = view.findViewById(R.id.et_email);

        init();
    }

    private void init() {
        willId = getArguments().getInt("will_id");

        cbLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isLocationShared = true;
                } else {
                    isLocationShared = false;
                }
            }
        });

        cbWill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isWillShared = true;
                } else {
                    isWillShared = false;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();

                requestAddShared(email);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void requestAddShared(final String email) {
        Log.d(TAG, "Request add shared");

        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Shared[] addShared = {new Shared(email, isWillShared, isLocationShared)};

        Call<StandardResponse> call = apiInterface.addShared(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, new AddShared(willId, addShared));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Upd Add Shared: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if(response.code() == 200 && response.isSuccessful()){
                    /*UpdWillDetailSharedFragment frag = (UpdWillDetailSharedFragment) getTargetFragment();
                    frag.refreshAdapter();
                    frag.updateDataEdit(itemIndex, email, sharedDoc, sharedLoc);
                    getDialog().dismiss();*/

                    UpdWillDetailSharedFragment frag = (UpdWillDetailSharedFragment) getTargetFragment();
                    frag.refreshAdapter();
                    frag.updateDataAdd(email, isWillShared, isLocationShared);
                    getDialog().dismiss();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }
}
