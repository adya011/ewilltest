package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Question;
import com.moonlay.litewill.model.Questions;
import com.moonlay.litewill.model.SecurityQuestionResponse;
import com.moonlay.litewill.model.ValidateResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecoverPin1Fragment extends BaseFragment {
    private TextView tvQuestion;
    private TextInputLayout etlEmail;
    private EditText etAnswer;
    private Button btnNext;
    private ApiInterface apiInterface;
    private String question = null;
    String answerStr = null;
    public static String TAG = "mydebug_recoverPin";
    String loginToken;
    SharedPrefManager sharedPrefManager;

    public RecoverPin1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_pin1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashboardActivity) getActivity()).setActionBarTitle("Change Email Address");
        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefManager = new SharedPrefManager(getContext());
        loginToken = sharedPrefManager.getLoginToken();
        initView();
    }

    private void initView() {
        tvQuestion = mView.findViewById(R.id.tv_question);
        etAnswer = mView.findViewById(R.id.et_answer);
        btnNext = mView.findViewById(R.id.btn_next);
        sharedPrefManager = new SharedPrefManager(getContext());
        etlEmail = mView.findViewById(R.id.etl_email);

        try {
            if (requestQuestion() == Constants.SUCCESS) {
                Log.d(TAG, "Success. Result Question: " + String.valueOf(question));
                tvQuestion.setText(question + "?");

            } else {
                Log.d(TAG, "FAILED");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerStr = etAnswer.getText().toString();

                try {
                    if (requestAnswer() == Constants.SUCCESS) {
                        Log.d(TAG, "Validate success");

                        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_tab1_layout, new RecoverPin2Fragment(), null);
                        ft.addToBackStack(null);
                        ft.commit();*/
                        fragmentTrans(R.id.frame_tab1_layout, new RecoverPin2Fragment(), null, null);

                    } else {
                        Log.d(TAG, "Failed");
                        showErrorDialog("Your answer is wrong");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String requestQuestion() throws InterruptedException {
        final String[] retVal = {null};

        /*Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = GetToken.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();*/

        Thread threadQuestion = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                String loggedInUser = sharedPrefManager.getUserLoggedIn();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<SecurityQuestionResponse> call = apiInterface.securityQuestion(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, loginToken, loggedInUser);

                try {
                    Response<SecurityQuestionResponse> resp = call.execute();
                    Log.d(TAG, "question resp code: " + resp.code());
                    Log.d(TAG, "user: " + loggedInUser);

                    if (resp.code() == 200 && resp.isSuccessful() == true) {
                        retVal[0] = Constants.SUCCESS;
                        question = resp.body().getResult();

                    } else {
                        retVal[0] = Constants.FAILED;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadQuestion.start();
        threadQuestion.join();

        return retVal[0];
    }

    private String requestAnswer() throws InterruptedException {
        final String[] retVal = {null};


        Thread threadAnswer = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                String loginToken = sharedPrefManager.getLoginToken();
                String loggedInUser = sharedPrefManager.getUserLoggedIn();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ValidateResponse> call = apiInterface.question(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, loginToken,
                        new Questions(new Question(question, answerStr), sharedPrefManager.getUserLoggedIn()));

                try {
                    Response<ValidateResponse> resp = call.execute();
                    Log.d(TAG, "answer resp code: " + resp.code());

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
        threadAnswer.start();
        threadAnswer.join();

        return retVal[0];
    }
}
