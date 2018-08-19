package com.moonlay.litewill.fragments.change;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ChangePin;
import com.moonlay.litewill.model.ChangePinResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePIN2Fragment extends BaseFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvError;
    SharedPrefManager sharedPrefManager;

    public ChangePIN2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pin2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        sharedPrefManager = new SharedPrefManager(getContext());
        Log.d("mydebug_changepin", "LoggedIn: " + sharedPrefManager.getUserLoggedIn());
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

        disableSoftInputFromAppearing(et1);
        disableSoftInputFromAppearing(et2);
        disableSoftInputFromAppearing(et3);
        disableSoftInputFromAppearing(et4);
        disableSoftInputFromAppearing(et5);
        disableSoftInputFromAppearing(et6);

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

        et3.setOnKeyListener(new View.OnKeyListener()

        {
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

        et6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et5.setText(null);
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

        btnBckSpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_DEL);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confNewPinStr = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();
                String newPin;

                Bundle b = getArguments();
                newPin = b.getString("newPin");

                if (confNewPinStr.equals(newPin)) {
                    try {
                        if (requestChangePin(newPin).equals("200")) {
                            Log.d("mydebug_changepin", "SUCCESS!");
                            fragmentTrans(R.id.frame_tab2_layout, new ChangePIN3Fragment(), null, "change_pin3");

                        } else {
                            showErrorDialog("Cannot change PIN number. Problem occured.");
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

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    private static void sendKey(EditText et, int keyCode) {
        BaseInputConnection baseInputConnection = new BaseInputConnection(et, true);
        KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        baseInputConnection.sendKeyEvent(kd);
    }

    private String requestChangePin(final String newPin) throws InterruptedException {
        final String[] retVal = {null};
        final String[] token = {null};
        final GetToken getRest = new GetToken();
        sharedPrefManager = new SharedPrefManager(getContext());

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = getRest.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();

        Thread threadChangePin = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                ApiInterface apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ChangePinResponse> call = apiInterface.changePin(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new ChangePin(sharedPrefManager.getUserLoggedIn(), newPin));

                try {
                    Response<ChangePinResponse> resp = call.execute();
                    Log.d("mydebug_changepin", "resp code: " + resp.code());
                    Log.d("mydebug_changepin", "pin: " + newPin);
                    Log.d("mydebug_changepin", "name: " + sharedPrefManager.getUserLoggedIn());
                    retVal[0] = String.valueOf(resp.code());

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
