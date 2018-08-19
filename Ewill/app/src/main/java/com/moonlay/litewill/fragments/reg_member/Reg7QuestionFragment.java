package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Reg7QuestionFragment extends BaseFragment {
    Spinner spinQuestion;
    EditText etAnswer;
    Button btnNext;
    TextView tvData;
    TextInputLayout etlAnswer;

    public Reg7QuestionFragment() {
        // Required empty public constructor
    }

    public static Reg7QuestionFragment newInstance(String email, String username, String password, String phonenumber, String pin) {
        Reg7QuestionFragment frag = new Reg7QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("phonenumber", phonenumber);
        bundle.putString("pin", pin);
        frag.setArguments(bundle);

        Log.d("mydebug_register", "reg7: " + email + " " + username + " " + password + " " + phonenumber + " " + pin);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg7_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
        ((RegMemberActivity) getActivity())
                .setActionBarTitle("Security Question");
    }

    private void initView() {
        spinQuestion = mView.findViewById(R.id.spinner_question);
        etAnswer = mView.findViewById(R.id.et_answer);
        btnNext = mView.findViewById(R.id.button_next7);
        etlAnswer = mView.findViewById(R.id.etl_answer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, Constants.secQuestion);
        spinQuestion.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getArguments().getString("email");
                String username = getArguments().getString("username");
                String password = getArguments().getString("password");
                String phone = getArguments().getString("phonenumber");
                String pin = getArguments().getString("pin");
                String questionStr = "What is your Mother's name";
                String answerStr = etAnswer.getText().toString();

                if (TextValidation.securityQuestionValidation(answerStr)) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_register, Reg8TncFragment.newInstance(email, username, password, phone, pin, questionStr, answerStr));
                    ft.addToBackStack("reg4");
                    ft.commit();

                } else {
                    etlAnswer.setErrorEnabled(true);
                    etlAnswer.setError(TextValidation.warning);
                }
            }
        });
    }
}
