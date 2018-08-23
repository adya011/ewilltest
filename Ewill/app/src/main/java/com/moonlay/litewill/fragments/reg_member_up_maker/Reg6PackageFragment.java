package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.StandaloneActionMode;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ProductListResponse;
import com.moonlay.litewill.utility.MyUtility;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg6PackageFragment extends BaseFragment {
    private static String TAG = "mydebug_regwill6";
    Button btnNext;
    AppCompatSpinner spPackage;
    ApiInterface apiInterface;
    int[] payPackage;
    String[] payPackageString;
    int payPackageListSize = 0;

    public Reg6PackageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg16_package, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        spPackage = mView.findViewById(R.id.sp_package);
        init();
    }

    private void init() {
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        getPackageList();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    private void getPackageList() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<ProductListResponse> call = apiInterface.productList(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                Log.d(TAG, "Product List: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    payPackageListSize = response.body().getResult().size();
                    payPackage = new int[payPackageListSize];
                    payPackageString = new String[payPackageListSize];
                    for (int i = 0; i < payPackageListSize; i++) {
                        try {
                            payPackage[i] = response.body().getResult().get(i).getUnitOfPeriod();
                            payPackageString[i] = response.body().getResult().get(i).getUnitOfPeriod() + " year(s)";
                            ((RegUpMemberActivity) getActivity()).productCode = response.body().getResult().get(i).getCode();

                        } catch (Exception e) {
                            Log.d(TAG, "Exception" + e);
                            Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT);
                        }
                    }

                    packageAdapter();

                } else {
                    MyUtility.alertDialogOK(getActivity(), "Connection Error");
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void packageAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.spinner_item, payPackageString);
        spPackage.setAdapter(adapter);
        spPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((RegUpMemberActivity) getActivity()).subscribePackage = payPackage[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void nextStep() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg7TncFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
