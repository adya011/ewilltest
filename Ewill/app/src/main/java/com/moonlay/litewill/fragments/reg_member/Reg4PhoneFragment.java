package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.TextValidation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg4PhoneFragment extends BaseFragment {
    Spinner spinPhoneCountry;
    TextView tvPhoneCountryCode;
    EditText etPhoneNumber;
    TextInputLayout etlPhoneNumber;
    Button btnNext;

    public Reg4PhoneFragment() {
        // Required empty public constructor
    }

    public static Reg4PhoneFragment newInstance(String email, String username, String password) {
        Reg4PhoneFragment frag = new Reg4PhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("username", username);
        bundle.putString("password", password);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg4_phone, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("Mobile Number");
    }

    private void initView() {
        spinPhoneCountry = mView.findViewById(R.id.spinner_phone_country_code);
        tvPhoneCountryCode = mView.findViewById(R.id.tv_phone_country_code);
        etPhoneNumber = mView.findViewById(R.id.et_phone_number);
        etlPhoneNumber = mView.findViewById(R.id.etl_phone_number);
        btnNext = mView.findViewById(R.id.btn_next);

        spinnerAdapter();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getArguments().getString("email");
                String username = getArguments().getString("username");
                String password = getArguments().getString("password");
                String phoneCodeStr = tvPhoneCountryCode.getText().toString();
                String phoneNumberStr = etPhoneNumber.getText().toString();

                if (TextValidation.phoneValidation(phoneNumberStr)) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_register, Reg5PinFragment.newInstance(email, username, password, phoneCodeStr + phoneNumberStr));
                    ft.addToBackStack("reg4");
                    ft.commit();
                } else {
                    etlPhoneNumber.setErrorEnabled(true);
                    etlPhoneNumber.setError(TextValidation.warning);
                }
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
