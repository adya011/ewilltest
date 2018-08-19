package com.moonlay.litewill.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moonlay.litewill.R;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    public Context mContext;
    public View mView;
    public Bundle bundle = new Bundle();

    private RelativeLayout rl_progress;
    private ProgressDialog mProgressDialog;

    protected SharedPreferences sharedPreferences;
    protected SharedPrefManager sharedPrefManager;
    protected String getPrefReg;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        getPrefReg = sharedPreferences.getString(Constants.REG_STATUS, Constants.DEFAULT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        sharedPrefManager = new SharedPrefManager(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void showLoadingProgress() {
        if (getActivity() != null) {
            if (rl_progress != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl_progress.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    protected void hideLoadingProgress() {
        if (getActivity() != null) {
            if (rl_progress != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl_progress.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    protected void showLoadingDialog(){
        showLoadingDialog(null);
    }

    protected void showLoadingDialog(final String message)
    {
        if (getActivity() != null)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        hideLoadingDialog();
                    else
                        mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    if (message == null)
                    {
                        String defaultMessage = getString(R.string.loading_message);
                        mProgressDialog.setMessage(defaultMessage);
                    } else
                    {
                        mProgressDialog.setMessage(message);
                    }
                    mProgressDialog.show();
                }
            });
        }
    }

    protected void hideLoadingDialog()
    {
        if (getActivity() != null)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.hide();
                }
            });
        }
    }

    protected void showToast(final String toastMessage)
    {
        if (getActivity() != null)
        {

            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    protected void fragmentTrans(int container, android.support.v4.app.Fragment goToFrag, Bundle bundle, String tag) {
        FragmentManager fm = getFragmentManager();
        /*FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);*/
        FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        goToFrag.setArguments(bundle);

        ft.replace(container, goToFrag, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    protected void showErrorDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert");
        builder.setPositiveButton("OK", null);
        builder.setMessage(msg);

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    public void fragmentBack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public static boolean textValidation(String textPattern, String editText){
        Pattern pattern;
        Matcher matcher;
        final String TEXT_PATTERN = textPattern;
        pattern = Pattern.compile(TEXT_PATTERN);
        matcher = pattern.matcher(editText);

        return matcher.matches();
    }

    public void textEnableTimer(final TextView tv){
        textEnable(tv, false);
        new CountDownTimer(5000, 10) {
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                /*tv.setEnabled(true);
                tv.setTextColor(getResources().getColor(R.color.edit_text));*/
                textEnable(tv, true);
                Log.d("resend", "enabled");
            }
        }.start();
    }

    public void textEnable(TextView tv, boolean bool){
        tv.setEnabled(bool);
        if(bool == true){
            tv.setTextColor(getResources().getColor(R.color.edit_text));
        }else {
            tv.setTextColor(getResources().getColor(R.color.edit_text_disabled));
        }
    }
}
