package com.moonlay.litewill.fragments.reg_member;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.LoginActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.VerifyPhone;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg10PhoneVerifyFragment extends BaseFragment {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    Button btnNext;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckspc;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvResend;

    String regUsername, tokenVerifyPhone;

    public Reg10PhoneVerifyFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String regUsername) {
        Reg10PhoneVerifyFragment fragment = new Reg10PhoneVerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.REG_USERNAME, regUsername);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg10_phone_verify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private static void sendKey(EditText et, int keyCode) {
        BaseInputConnection baseInputConnection = new BaseInputConnection(et, true);
        KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        baseInputConnection.sendKeyEvent(kd);
    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(getContext());
        sharedPrefManager.saveSPString(Constants.REG_STATUS, Constants.REG_WAIT_VER_PHONE);
        regUsername = sharedPrefManager.getRegUsername();
        apiInterface = RestProvider.getClient().create(ApiInterface.class);

        tvResend = mView.findViewById(R.id.tv_resend);
        tvResend.setEnabled(true);

        et1 = mView.findViewById(R.id.et_1);
        et2 = mView.findViewById(R.id.et_2);
        et3 = mView.findViewById(R.id.et_3);
        et4 = mView.findViewById(R.id.et_4);
        et5 = mView.findViewById(R.id.et_5);
        et6 = mView.findViewById(R.id.et_6);
        btnNext = mView.findViewById(R.id.button_next10);

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
        btnBckspc = mView.findViewById(R.id.btn_bckspc);

        et1.requestFocus();

        et1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et2.requestFocus();
                }
                return false;
            }
        });

        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et3.requestFocus();
                }

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et1.setText(null);
                    et1.requestFocus();
                }

                return false;
            }
        });

        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et4.requestFocus();
                }

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et2.setText(null);
                    et2.requestFocus();
                }


                return false;
            }
        });

        et4.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et5.requestFocus();
                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et3.setText(null);
                    et3.requestFocus();
                }
                return false;
            }
        });

        et5.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et6.requestFocus();
                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et4.setText(null);
                    et4.requestFocus();
                }
                return false;
            }
        });

        et6.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et5.setText(null);
                    et5.requestFocus();
                }
                return false;
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_5);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_6);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_7);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_8);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_9);
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_0);
            }
        });

        btnBckspc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_DEL);
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEnable(tvResend, false);
                Log.d("resend", "disabled");
                textEnableTimer(tvResend);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenVerifyPhone = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();

                try {
                    if (requestVerifyPhone().equals(Constants.SUCCESS)) {
                        Log.d(RegMemberActivity.TAG, "request verify phone: " + requestVerifyPhone());
                        sharedPrefManager.removeRegStatus();
                        Log.d(RegMemberActivity.TAG, "register status: " + sharedPrefManager.getRegStatus());

                        /*FragmentManager fm = getFragmentManager();
                        fm.popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);*/

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        sharedPrefManager.removeRegStatus();
                        sharedPrefManager.removeRegUsername();

                    } else {
                        Log.d(RegMemberActivity.TAG, "request verify phone: " + requestVerifyPhone());
                        Log.d(RegMemberActivity.TAG, "register status: " + sharedPrefManager.getRegStatus());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String requestVerifyPhone() throws InterruptedException {
        final String[] retVal = {null};
        final String[] token = {null};

        Log.d(RegUpMemberActivity.TAG, "request verify phone");

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = GetToken.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();

        Thread threadVerifyPhone = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();

                Call<StandardResponse> call = apiInterface.verifyPhone(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                        token[0],
                        new VerifyPhone(regUsername, tokenVerifyPhone));

                try {
                    Response<StandardResponse> resp = call.execute();
                    Log.d(RegMemberActivity.TAG, "verify phone username: " + regUsername);
                    Log.d(RegMemberActivity.TAG, "verify phone token phone: " + tokenVerifyPhone);
                    Log.d(RegMemberActivity.TAG, "verify phone resp code: " + resp.code());
                    Log.d(RegMemberActivity.TAG, "verify phone success: " + resp.isSuccessful());

                    if (resp.code() == 200 && resp.isSuccessful()) {
                        retVal[0] = Constants.SUCCESS;

                    } else {
                        retVal[0] = Constants.FAILED;
                        Log.d(RegMemberActivity.TAG, "url: " + resp.raw().request().url());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadVerifyPhone.start();
        threadVerifyPhone.join();

        return retVal[0];
    }
}
