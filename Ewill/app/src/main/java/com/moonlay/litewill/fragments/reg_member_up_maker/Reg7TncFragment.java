package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.UpgradeMaker;
import com.moonlay.litewill.model.UpgradeMakerResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg7TncFragment extends BaseFragment {
    private static final String TAG = "mydebug_reg15";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    Button btnNext;

    private String passportNo;
    private String fullName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationality;
    private String gender;
    private String address;
    private boolean isSingaporean;

    public Reg7TncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg8_tnc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.button_next8);

        initView();
    }

    private void initView() {
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        passportNo = ((RegUpMemberActivity) getActivity()).passportNo;
        fullName = ((RegUpMemberActivity) getActivity()).fullName;
        dateOfBirth = ((RegUpMemberActivity) getActivity()).dob;
        placeOfBirth = ((RegUpMemberActivity) getActivity()).pob;
        nationality = ((RegUpMemberActivity) getActivity()).nationality;
        gender = ((RegUpMemberActivity) getActivity()).gender;
        address = ((RegUpMemberActivity) getActivity()).address;
        isSingaporean = ((RegUpMemberActivity) getActivity()).isSingaporean;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestUpMaker();
            }
        });
    }

    private void requestUpMaker() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<UpgradeMakerResponse> call = apiInterface.upgradeMaker(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new UpgradeMaker(passportNo, fullName, dateOfBirth, placeOfBirth, nationality, gender, address, isSingaporean));
        call.enqueue(new Callback<UpgradeMakerResponse>() {
            @Override
            public void onResponse(Call<UpgradeMakerResponse> call, Response<UpgradeMakerResponse> response) {
                Log.d(TAG, "Up Maker: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    nextStep();
                } else {
                    Log.d(TAG, "url: " + response.raw().request().url());
                    Log.d(TAG, "passNo: " + passportNo + ", fullName: " + fullName + ", dob: " + dateOfBirth
                            + ", pob: " + placeOfBirth + ", nationality: " + nationality + ", gender: " + gender
                            + ", address: " + address + ", isSingapore: " + isSingaporean);
                }
            }

            @Override
            public void onFailure(Call<UpgradeMakerResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void nextStep() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg8PaymentFragment());
        ft.addToBackStack("reg15");
        ft.commit();
    }
}
