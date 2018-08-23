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

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePIN1Fragment extends BaseFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvError;

    public ChangePIN1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pin1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        //((DashboardActivity) getActivity()).setActionBarTitle("Change PIN");
        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                String newPinStr = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();
                Log.d("mydebug_changepin", "" + newPinStr);

                ChangePIN2Fragment frag = new ChangePIN2Fragment();
                Bundle b = new Bundle();
                b.putString("newPin", newPinStr);
                fragmentTrans(R.id.frame_tab2_layout, frag, b, "change_pin1");
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
}
