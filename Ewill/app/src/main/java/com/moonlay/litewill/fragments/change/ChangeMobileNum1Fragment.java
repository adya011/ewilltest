package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ChangePhone;
import com.moonlay.litewill.model.StandardResponse;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMobileNum1Fragment extends BaseFragment {
    Button btnNext;
    Spinner spinPhoneCountry;
    TextView tvPhoneCountryCode;
    EditText etPhoneNumber;
    TextInputLayout etlPhoneNumber;

    private static String TAG = "mydebug_chgPhone";
    ApiInterface apiInterface;
    String userName;

    public ChangeMobileNum1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_mobile_num1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((DashboardActivity) getActivity()).setActionBarTitle("Change Mobile Number");
        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinPhoneCountry = mView.findViewById(R.id.spinner_phone_country_code);
        tvPhoneCountryCode = mView.findViewById(R.id.tv_phone_country_code);
        etPhoneNumber = mView.findViewById(R.id.et_phone_number);
        etlPhoneNumber = mView.findViewById(R.id.etl_phone_number);
        btnNext = mView.findViewById(R.id.btn_next);

        init();
    }

    private void init() {
        apiInterface = RestProvider.getClient().create(ApiInterface.class);

        spinnerAdapter();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countryCode = tvPhoneCountryCode.getText().toString();
                String phoneNum = etPhoneNumber.getText().toString();
                String newPhoneNumber = countryCode + phoneNum;

                requestChangePhone(newPhoneNumber);
            }
        });
    }

    private void requestChangePhone(final String phoneNumber) {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();
        userName = sharedPrefManager.getUserLoggedIn();

        Call<StandardResponse> call = apiInterface.changePhone(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new ChangePhone(userName, phoneNumber));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Change Phone: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                Log.d(TAG, "Change to: " + phoneNumber);

                if (response.code() == 200 && response.isSuccessful()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_tab2_layout, ChangeMobileNum2Fragment.newInstance(phoneNumber));
                    ft.commit();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void spinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, Constants.phoneCountry);
        spinPhoneCountry.setAdapter(adapter);

        spinPhoneCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        tvPhoneCountryCode.setText("" + Constants.phoneCode[i]);
                        break;
                    case 1:
                        tvPhoneCountryCode.setText("" + Constants.phoneCode[i]);
                        break;
                    case 2:
                        tvPhoneCountryCode.setText("" + Constants.phoneCode[i]);
                        break;
                    case 3:
                        tvPhoneCountryCode.setText("" + Constants.phoneCode[i]);
                        break;
                    case 4:
                        tvPhoneCountryCode.setText("" + Constants.phoneCode[i]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
