package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.fragments.BaseFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg4PassportFragment extends BaseFragment {
    public static final String TAG = "mydebug_passport";
    static final int DATE_PICKER_ID = 1111;
    Button btnNext;
    ImageButton datePicker;
    EditText etFullName, etPob;
    TextView tvDob;
    int year, month, day;

    public Reg4PassportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg13_passport, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        etFullName = mView.findViewById(R.id.et_fullname);
        tvDob = mView.findViewById(R.id.tv_dob);
        etPob = mView.findViewById(R.id.et_pob);
        datePicker = mView.findViewById(R.id.datepicker);
        init();
    }

    private void init() {
        Log.d(TAG, "init");

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Date currentDate = Calendar.getInstance().getTime();

        etFullName.setText(((RegUpMemberActivity) getActivity()).fullName);
        if (((RegUpMemberActivity) getActivity()).dob != null) {
            tvDob.setText(((RegUpMemberActivity) getActivity()).dob);
        } else {
            tvDob.setText(currentDate.toString());
        }
        etPob.setText(((RegUpMemberActivity) getActivity()).pob);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegUpMemberActivity) getActivity()).fullName = etFullName.getText().toString();
                /*((RegUpMemberActivity) getActivity()).dob = tvDob.getText().toString();*/
                ((RegUpMemberActivity) getActivity()).dob = tvDob.getText().toString();
                ((RegUpMemberActivity) getActivity()).pob = etPob.getText().toString();

                goToNextFrag();

                Log.d(TAG, "+++ To Reg14 Fragment +++");
            }
        });
    }

    private void goToNextFrag() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg5PassportFragment());
        ft.addToBackStack("reg13");
        ft.commit();
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, datePickerListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select Date");
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String yy = String.valueOf(year);
            String mm = String.valueOf(month);
            String dd = String.valueOf(dayOfMonth);

            tvDob.setText(yy + "-" + mm + "-" + dd);
        }
    };
}
