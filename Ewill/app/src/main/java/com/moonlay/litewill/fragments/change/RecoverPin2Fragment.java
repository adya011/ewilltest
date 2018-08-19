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
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.OnScreenKeyboard;
import com.moonlay.litewill.utility.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecoverPin2Fragment extends BaseFragment {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext;
    EditText et1, et2, et3, et4, et5, et6;
    TextView tvError;

    String newPin;
    SharedPrefManager sharedPrefManager;

    String TAG = "mydebug_recoverpin";

    public RecoverPin2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_pin2, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        hideKeyboard(getActivity());
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

        OnScreenKeyboard osk = new OnScreenKeyboard();
        osk.PinScreen(et1, et2, et3, et4, et5, et6, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBckSpc, btnNext, tvError);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPin = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();

                /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_tab1_layout, RecoverPin3Fragment.newInstance(newPin));
                ft.addToBackStack(null);
                ft.commit();*/
                Bundle args = new Bundle();
                args.putString("newpin", newPin);

                fragmentTrans(R.id.frame_tab1_layout, RecoverPin3Fragment.newInstance(newPin), args, null);

                Log.d(TAG, "newpin: " + newPin);
            }
        });
    }
}
