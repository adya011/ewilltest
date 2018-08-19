package com.moonlay.litewill.dialogfragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.model.ValidatePin;
import com.moonlay.litewill.model.ValidateResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinFragment extends DialogFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnOk;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvPinTitle, tvError;
    String loginToken;
    String userLoggedIn;
    ApiInterface apiInterface;
    static String TAG = "mydebug_pinDialog";

    public PinFragment() {
    }

    public static PinFragment newInstance(String title) {
        PinFragment frag = new PinFragment();
        frag.setCancelable(false);
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        initView(view);
    }

    private String requestValPin(final String pin) throws InterruptedException {
        final String[] retVal = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ValidateResponse> call = apiInterface.validatePin(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, loginToken,
                        new ValidatePin(pin, userLoggedIn));
                try {
                    Response<ValidateResponse> resp = call.execute();
                    Log.d(TAG, "resp code: " + resp.code());
                    Log.d(TAG, "username: " + userLoggedIn);
                    Log.d(TAG, "pin: " + pin);

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
        thread.start();
        thread.join();

        return retVal[0];
    }

    private void initView(View view) {
        et1 = view.findViewById(R.id.et_1);
        et2 = view.findViewById(R.id.et_2);
        et3 = view.findViewById(R.id.et_3);
        et4 = view.findViewById(R.id.et_4);
        et5 = view.findViewById(R.id.et_5);
        et6 = view.findViewById(R.id.et_6);
        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        btn4 = view.findViewById(R.id.btn_4);
        btn5 = view.findViewById(R.id.btn_5);
        btn6 = view.findViewById(R.id.btn_6);
        btn7 = view.findViewById(R.id.btn_7);
        btn8 = view.findViewById(R.id.btn_8);
        btn9 = view.findViewById(R.id.btn_9);
        btn0 = view.findViewById(R.id.btn_0);
        btnBckSpc = view.findViewById(R.id.btn_bckspc);
        btnOk = view.findViewById(R.id.btn_ok_pin);
        tvPinTitle = view.findViewById(R.id.tv_pin_title);
        tvError = view.findViewById(R.id.tv_error_message);

        et1.requestFocus();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());

        loginToken = sharedPrefManager.getLoginToken();
        userLoggedIn = sharedPrefManager.getUserLoggedIn();
        Log.d("mydebug_pin", "login token: " + loginToken);

        String title = getArguments().getString("title", "Enter Name");
        tvPinTitle.setText(title);

        et1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et2.requestFocus();
                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et1.requestFocus();
                }
                return false;
            }
        });

        et2.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    et3.requestFocus();
                }

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et2.setText(null);
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
                    et3.setText(null);
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
                    et4.setText(null);
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
                    et5.setText(null);
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
                    et6.setText(null);
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

        btnBckSpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendKey(et1, KeyEvent.KEYCODE_DEL);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPin = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();
                try {
                    if(enteredPin.length() == 6){
                        if (requestValPin(enteredPin).equals(Constants.SUCCESS)) {
                            getDialog().dismiss();
                        } else {
                            tvError.setText("Invalid Pin");
                        }
                    }else {
                        tvError.setText("Fill pin");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void sendKey(EditText et, int keyCode) {
        BaseInputConnection baseInputConnection = new BaseInputConnection(et, true);
        KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        baseInputConnection.sendKeyEvent(kd);
    }

}
