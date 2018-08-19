package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ChangePin;
import com.moonlay.litewill.model.ChangePinResponse;
import com.moonlay.litewill.utility.OnScreenKeyboard;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecoverPin3Fragment extends BaseFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvError;

    String newPinConf, newPin, userName;
    String TAG = "mydebug_recoverPin";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    String loginToken;

    public RecoverPin3Fragment() {
        // Required empty public constructor
    }

    public static RecoverPin3Fragment newInstance(String newPin) {
        RecoverPin3Fragment fragment = new RecoverPin3Fragment();
        Bundle args = new Bundle();
        args.putString("newpin", newPin);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_pin3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getContext());
        loginToken = sharedPrefManager.getLoginToken();
        initView();
    }

    private void initView() {
        et1 = mView.findViewById(R.id.et_1);
        et2 = mView.findViewById(R.id.et_2);
        et3 = mView.findViewById(R.id.et_3);
        et4 = mView.findViewById(R.id.et_4);
        et5 = mView.findViewById(R.id.et_5);
        et6 = mView.findViewById(R.id.et_6);
        btn1 = mView.findViewById(R.id.btn_1);
        btn2 = mView.findViewById(R.id.btn_2);
        btn3 = mView.findViewById(R.id.btn_3);
        btn4 = mView.findViewById(R.id.btn_4);
        btn5 = mView.findViewById(R.id.btn_5);
        btn6 = mView.findViewById(R.id.btn_6);
        btn7 = mView.findViewById(R.id.btn_7);
        btn8 = mView.findViewById(R.id.btn_8);
        btn9 = mView.findViewById(R.id.btn_9);
        btn0 = mView.findViewById(R.id.btn_0);
        btnBckSpc = mView.findViewById(R.id.btn_bckspc);
        btnNext = mView.findViewById(R.id.btn_next);
        tvError = mView.findViewById(R.id.tv_error_message);

        sharedPrefManager = new SharedPrefManager(getContext());
        userName = sharedPrefManager.getUserLoggedIn();

        OnScreenKeyboard osk = new OnScreenKeyboard();
        osk.PinScreen(et1, et2, et3, et4, et5, et6, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext, tvError);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPinConf = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();
                newPin = getArguments().getString("newpin");

                Log.d(TAG, "newpin: " + newPin + " newpin confirm:" + newPinConf);

                if (newPinConf.equals(newPin)) {
                    try {
                        if (requestChangePin(userName, newPin).equals(Constants.SUCCESS)) {
                            Log.d(TAG, "RECOVER PIN SUCCESS");

                            /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_tab1_layout, new RecoverPin4Fragment());
                            ft.addToBackStack(null);
                            ft.commit();*/

                            fragmentTrans(R.id.frame_tab1_layout, new RecoverPin4Fragment(), null, null);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    tvError.setText("Confirm PIN does not match");
                }

            }
        });
    }

    private String requestChangePin(final String userName, final String newPin) throws InterruptedException {
        final String[] retVal = {null};
        final String[] token = {null};

        /*Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = GetToken.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();*/

        Thread threadChangePin = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ChangePinResponse> call = apiInterface.changePin(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, loginToken,
                        new ChangePin(userName, newPin));

                try {
                    Response<ChangePinResponse> resp = call.execute();
                    Log.d(TAG, "resp code: " + resp.code());
                    Log.d(TAG, "pin: " + newPin);
                    Log.d(TAG, "username: " + userName);

                    if (resp.code() == 200 && resp.isSuccessful() == true) {
                        retVal[0] = Constants.SUCCESS;

                    } else {
                        retVal[0] = Constants.FAILED;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadChangePin.start();
        threadChangePin.join();

        return retVal[0];
    }
}
