package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.VerifyChangePhone;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMobileNum2Fragment extends BaseFragment {
    Button btnNext;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckspc;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvResend;

    String userName, newPhoneNum;

    private static String TAG = "mydebug_chgPhone";
    ApiInterface apiInterface;

    String regUsername, tokenVerifyPhone;

    public ChangeMobileNum2Fragment() {
        // Required empty public constructor
    }

    public static ChangeMobileNum2Fragment newInstance(String newPhoneNum) {
        ChangeMobileNum2Fragment frag = new ChangeMobileNum2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("new_phone_num", newPhoneNum);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_mobile_num2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);

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

        init();
    }

    private void init() {
        apiInterface = RestProvider.getClient().create(ApiInterface.class);
        userName = sharedPrefManager.getUserLoggedIn();
        newPhoneNum = getArguments().getString("new_phone_num");

        numpadButton();

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
            public void onClick(View v) {
                tokenVerifyPhone = et1.getText().toString() + et2.getText().toString() +
                        et3.getText().toString() + et4.getText().toString() + et5.getText().toString() +
                        et6.getText().toString();
                requestConfirmChangePhone(tokenVerifyPhone, newPhoneNum);
            }
        });
    }

    private void requestConfirmChangePhone(String tokenVerify, String newPhoneNum) {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<StandardResponse> call = apiInterface.verifyChangePhone(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new VerifyChangePhone(userName, tokenVerify, newPhoneNum));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Verify Change Phone: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    private void numpadButton() {
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
    }

    private static void sendKey(EditText et, int keyCode) {
        BaseInputConnection baseInputConnection = new BaseInputConnection(et, true);
        KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        baseInputConnection.sendKeyEvent(kd);
    }

}
