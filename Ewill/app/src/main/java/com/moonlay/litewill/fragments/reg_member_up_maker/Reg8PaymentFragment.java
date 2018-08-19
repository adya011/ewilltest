package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.RegWillActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.will.NoWillNotifyFragment;
import com.moonlay.litewill.utility.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg8PaymentFragment extends BaseFragment {
    Button btnNext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    public Reg8PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg16_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        sharedPrefManager = new SharedPrefManager(getContext());
    }

    private void initView() {
        btnNext = mView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveWillDialog();
            }
        });
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
    }


    private void haveWillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you have a will?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registerWillDialog();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), DashboardActivity.class));
            }
        });

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    private void registerWillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want register your will now?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), RegWillActivity.class));
                getActivity().finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), DashboardActivity.class));
                gotoNotifyNoWill();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void gotoNotifyNoWill() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new NoWillNotifyFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
