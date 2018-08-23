package com.moonlay.litewill.fragments.home;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.OpenWillActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.SearchActivity;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.will.NoWillNotifyFragment;
import com.moonlay.litewill.fragments.will.OpenWillsFragment;
import com.moonlay.litewill.utility.SharedPrefManager;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * DashboardActivity
 */
public class HomeFragment extends BaseFragment {
    Button btnMyWill, btnSearchWill, btnOpenWill;
    int userMemberType;
    SharedPrefManager sharedPrefManager;

    public static final String TAG = "mydebug_home";
    public static final String ACTION_ID = "action_id";
    public static final String GOTO_REGISTER_WILL = "go_to_register_will";
    public static final String GOTO_MYWILL = "go_to_mywill";

    private static final int USER_STATUS = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "++ Home Fragment ++");
        btnMyWill = mView.findViewById(R.id.btn_mywill);
        btnSearchWill = mView.findViewById(R.id.btn_searchwill);
        btnOpenWill = mView.findViewById(R.id.btn_openwill);

        //((DashboardActivity) getActivity()).setActionBarTitle("Home");
        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initView();
    }

    private void initView() {
        sharedPrefManager = new SharedPrefManager(getContext());
        userMemberType = sharedPrefManager.getUserMemberType();
        Log.d(TAG, "init. User member type: " + userMemberType);
        Log.d(TAG, "not count: " + sharedPrefManager.getNotificationCount());

        btnMyWill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getContext());*/
                /*SharedPreferences saved_values = getContext().getSharedPreferences("ewillSp", Context.MODE_PRIVATE);
                int count = saved_values.getInt("notif_count", 0);
                SharedPreferences.Editor editor = saved_values.edit();
                editor.putInt("notif_count", count + 1);
                editor.commit();

                Log.d(TAG, "shared pref count: " + saved_values.getInt("notif_count", 0));*/

                sharedPrefManager.addNotificationCount(1);
                Log.d(TAG, "shared pref count: " + sharedPrefManager.getNotificationCount());


                if (userMemberType == 1 || userMemberType == 2) {
                    showMyWillQuestionDialog();

                } else if (userMemberType == 3) {
                    goToMyWill();

                } else {
                    showAccessDeniedDialog();
                }
                /*testNoWill();*/
            }
        });

        btnSearchWill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMemberType != 1) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));

                } else {
                    showUpSearcherRequiredDialog();
                }
            }
        });

        btnOpenWill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOpenWill();
            }
        });
    }

    private void goToMyWill() {
        Log.d(TAG, "-- Go To MyWill --");

        Intent intent = new Intent(getActivity(), WillActivity.class);
        intent.putExtra("will_intent", "go_to_my_wills");
        startActivity(intent);
    }

    private void goToOpenWill() {
        Log.d(TAG, "-- Go To Open Will --");

        Intent intent = new Intent(getActivity(), OpenWillActivity.class);
        intent.putExtra("will_intent", "go_to_open_will");
        startActivity(intent);
    }

    protected void showMyWillQuestionDialog() {
        Log.d(TAG, "-- showMyWillDialog --");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), RegUpMemberActivity.class);
                intent.putExtra("up_member_intent", "up_maker");
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        builder.setMessage("You must update your information to use this feature. " +
                "Do you want to update your information?");

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    private void showUpSearcherRequiredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), RegUpMemberActivity.class);
                intent.putExtra("up_member_intent", "up_searcher");
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        builder.setMessage("Your account does not support this function. " +
                "Do you want to upgrade your account");

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    private void showAccessDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Ok", null);
        builder.setMessage("Your account does not support this function.");

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }
}
