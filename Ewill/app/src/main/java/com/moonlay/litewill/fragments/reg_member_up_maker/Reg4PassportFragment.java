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
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.MyUtility;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg4PassportFragment extends BaseFragment {
    public static final String TAG = "mydebug_passport";

    Button btnNext;
    ImageButton datePicker;
    EditText etFullName, etPob;
    TextView tvDob;

    String displayFormat = "dd MMM yyyy";
    String restFormat = "yyyy-MM-dd";

    Date pickedDate;

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
        initForm();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showDatePicker();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickedDate.before(getMinDob())) {
                    ((RegUpMemberActivity) getActivity()).fullName = etFullName.getText().toString();
                    ((RegUpMemberActivity) getActivity()).dob = dateToString(pickedDate, restFormat);
                    ((RegUpMemberActivity) getActivity()).pob = etPob.getText().toString();

                    goToNextFrag();

                    Log.d(TAG, "+++ To Reg14 Fragment +++");

                } else {
                    String alert = getResources().getString(R.string.min_age);
                    MyUtility.alertDialogOK(getActivity(), alert);
                }
            }
        });
    }

    private void initForm() {
        etFullName.setText(((RegUpMemberActivity) getActivity()).fullName);
        if (((RegUpMemberActivity) getActivity()).dob != null) {   //Check from scan or manual input
            try {
                Date scannedDate = stringToDate(((RegUpMemberActivity) getActivity()).dob);
                pickedDate = scannedDate;
                Log.d(TAG, "scanned date: " + scannedDate);
                String displayDate = dateToString(scannedDate, displayFormat);
                tvDob.setText(displayDate);

            } catch (ParseException e) {
                pickedDate = getMinDob();
                tvDob.setText(dateToString(getMinDob(), displayFormat));
            }

        } else {
            tvDob.setText(dateToString(getMinDob(), displayFormat));   //if skipped set date to minimum dob
        }
        etPob.setText(((RegUpMemberActivity) getActivity()).pob);
    }

    private Date getMinDob() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.YEAR, -Constants.MIN_AGE); //set calendar to minimum dob
        Date defaultDate = calendar.getTime(); //default date: minimum dob

        return defaultDate;
    }

    private String dateToString(Date date, String newFormat) {    //Convert date to String with
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
        dateFormat.applyPattern(newFormat);
        String convertedDefaultDate = dateFormat.format(date);
        Log.d(TAG, "converted date: " + convertedDefaultDate);

        return convertedDefaultDate;
    }

    private Date stringToDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date convertedDate = dateFormat.parse(dateString);

        return convertedDate;
    }

    private void showDatePicker() throws ParseException {
        String getDateStr = tvDob.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat(displayFormat);
        Date getDate = dateFormat.parse(getDateStr);
        Log.d(TAG, "get Date: " + getDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT,
                datePickerListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Date of Birth");
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;

            //Convert integer into 2 digit
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String dayDual = decimalFormat.format(dayOfMonth);
            String monthDual = decimalFormat.format(month);
            String yearDual = decimalFormat.format(year);

            String yy = String.valueOf(yearDual);
            String mm = String.valueOf(monthDual);
            String dd = String.valueOf(dayDual);

            String pickedDateStr = dd + mm + yy;
            Log.d(TAG, "picked date string: " + pickedDateStr);
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");

            String convertedDate;

            try {
                Date date = dateFormat.parse(pickedDateStr);
                dateFormat.applyPattern(displayFormat);
                convertedDate = dateFormat.format(date);
                Log.d(TAG, "Date: " + date);
                tvDob.setText(convertedDate);
                pickedDate = date;

                if (date.after(getMinDob())) {
                    Log.d(TAG, "Restricted Age");

                } else {
                    Log.d(TAG, "Age Allowed");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "Date Picker: " + dayOfMonth + "-" + month + "-" + year);
        }
    };

    private void goToNextFrag() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg5PassportFragment());
        ft.addToBackStack("reg13");
        ft.commit();
    }
}
