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
import com.moonlay.litewill.model.DeleteShared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpdateShared;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdEditSharedDialog extends DialogFragment {
    private static String TAG = "mydebug_updshrdet";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    Button btnSave, btnCancel;
    ImageButton btnDelete;
    CheckBox cbLocation, cbDocument;
    TextInputEditText etEmail;

    int itemIndex, sharedId;
    String sharedEmail;
    boolean sharedDoc;
    boolean sharedLoc;

    public UpdEditSharedDialog() {
        // Required empty public constructor
    }

    public static UpdEditSharedDialog newInstance(int itemIndex, int sharedId, String sharedEmail,
                                                  boolean sharedDoc, boolean sharedLoc) {
        UpdEditSharedDialog frag = new UpdEditSharedDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("index", itemIndex);
        bundle.putInt("shared_id", sharedId);
        bundle.putString("shared_email", sharedEmail);
        bundle.putBoolean("shared_doc", sharedDoc);
        bundle.putBoolean("shared_loc", sharedLoc);
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
        btnCancel = view.findViewById(R.id.btn_no);
        btnSave = view.findViewById(R.id.btn_yes);
        btnDelete = view.findViewById(R.id.btn_delete);

        cbDocument = view.findViewById(R.id.cb_will);
        cbLocation = view.findViewById(R.id.cb_location);
        etEmail = view.findViewById(R.id.et_email);

        sharedPrefManager = new SharedPrefManager(getContext());

        init();
    }

    private void init() {
        itemIndex = getArguments().getInt("index");
        sharedId = getArguments().getInt("shared_id");
        sharedEmail = getArguments().getString("shared_email");
        sharedDoc = getArguments().getBoolean("shared_doc");
        sharedLoc = getArguments().getBoolean("shared_loc");

        etEmail.setText(sharedEmail);
        cbDocument.setChecked(sharedDoc);
        cbLocation.setChecked(sharedLoc);

        Log.d(TAG, "share id: " + sharedId);
        Log.d(TAG, "share email: " + sharedEmail);
        Log.d(TAG, "share loc: " + sharedLoc);
        Log.d(TAG, "share doc: " + sharedDoc);

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        cbLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedLoc = true;
                } else {
                    sharedLoc = false;
                }
            }
        });

        cbDocument.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedDoc = true;
                } else {
                    sharedDoc = false;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpdateSharedDetail();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteWill();
            }
        });
    }

    //Delete shared will
    private void requestDeleteWill() {
        Log.d(TAG, "-- request delete will --");

        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<StandardResponse> call = apiInterface.deleteShared(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, new DeleteShared(sharedId));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Will Delete Shared: API response code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    getDialog().dismiss();

                    UpdWillDetailSharedFragment frag = (UpdWillDetailSharedFragment) getTargetFragment();
                    frag.refreshAdapter();
                    frag.refreshData();
                    frag.updateDataDelete(itemIndex);

                } else {
                    Log.d(TAG, "url: " + response.raw().request().url());
                    Log.d(TAG, "shared id: " + sharedId);
                }

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    //Update shared will
    private void requestUpdateSharedDetail() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        final String email = etEmail.getText().toString();

        Call<StandardResponse> call = apiInterface.updateWillShared(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, new UpdateShared(sharedId, email, sharedDoc, sharedLoc));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Will Update Shared: API response code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                Log.d(TAG, "loc shared: " + sharedLoc + ", will shared: " + sharedDoc);

                if (response.code() == 200 && response.isSuccessful()) {
                    UpdWillDetailSharedFragment frag = (UpdWillDetailSharedFragment) getTargetFragment();
                    frag.refreshAdapter();
                    frag.updateDataEdit(itemIndex, email, sharedDoc, sharedLoc);
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
