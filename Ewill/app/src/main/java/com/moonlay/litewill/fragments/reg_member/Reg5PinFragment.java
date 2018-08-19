package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.OnScreenKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg5PinFragment extends BaseFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvError;

    public Reg5PinFragment() {
        // Required empty public constructor
    }

    public static Reg5PinFragment newInstance(String email, String username, String password, String phonenumber) {
        Reg5PinFragment frag = new Reg5PinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("phonenumber", phonenumber);
        frag.setArguments(bundle);

        Log.d("mydebug_register", "reg5: " + email + " " + username + " " + password + " " + phonenumber);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg5_pin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("PIN");
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
        btnNext = mView.findViewById(R.id.btn_next5);

        OnScreenKeyboard osk = new OnScreenKeyboard();
        osk.PinScreen(et1, et2, et3, et4, et5, et6, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext, tvError);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getArguments().getString("email");
                String username = getArguments().getString("username");
                String password = getArguments().getString("password");
                String phoneNumber = getArguments().getString("phonenumber");
                String pinStr = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_register, Reg6PinConfFragment.newInstance(email, username, password, phoneNumber, pinStr));
                ft.addToBackStack("reg5");
                ft.commit();
            }
        });
    }
}
