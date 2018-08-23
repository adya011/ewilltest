package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.ValidDocumentTypes;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.MyUtility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import xavier.blacksharktech.com.xavierlib.XavierActivity;
import xmlwise.Plist;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.moonlay.litewill.R.raw.xavier;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg2ScanFragment extends BaseFragment {
    public static final String TAG = "mydebug_passport2";
    Button btnScan, btnNext;

    private String idNumber;
    private String fullName;
    private String gender;
    private String dob;
    private String pob;
    private String nationality;

    public static final String MRZ_KEY_VALUE_MAP = "MrzKeyValueMap";
    public static final int MRZ_REQUEST = 1;
    public static final int USER_DOCTYPE_SELECTION_REQUEST = 2;
    public static final String PASSPORT_DOC_TYPE = "P";

    public Reg2ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg2_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = mView.findViewById(R.id.btn_skip);
        btnScan = mView.findViewById(R.id.btn_scan);

        init();
    }

    private void init() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        MyUtility.askForPermission(getActivity(), getContext(), permissions);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startXavierActivity();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextFrag();
            }
        });

    }

    private void goToNextFrag() {
        Log.d(TAG, "go to frag 3");

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg3PassportFragment());
        ft.addToBackStack("reg12");
        ft.commit();

        Log.d(TAG, "+++ To Reg13 Fragment +++");
        Log.d(TAG, "passport id:" + ((RegUpMemberActivity) getActivity()).passportNo);
        Log.d(TAG, "full name:" + ((RegUpMemberActivity) getActivity()).fullName);
        Log.d(TAG, "dob:" + ((RegUpMemberActivity) getActivity()).dob);
        Log.d(TAG, "pob:" + ((RegUpMemberActivity) getActivity()).pob);
        Log.d(TAG, "nationality:" + ((RegUpMemberActivity) getActivity()).nationality);
        Log.d(TAG, "gender:" + ((RegUpMemberActivity) getActivity()).gender);
    }


    public void startXavierActivity() {
        Intent intent = new Intent(getActivity(), XavierActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        HashMap<String, Object> properties = null;
        try {
            // Please refer to README for further instruction on how to create the raw xavier file
            InputStream inputStream = getResources().openRawResource(xavier);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                properties = (HashMap<String, Object>) Plist.fromXml(sb.toString());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                br.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        intent.putExtra(XavierActivity.SETTINGS, properties);

        startActivityForResult(intent, MRZ_REQUEST);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "+++ onActivityResult() +++");

        if (requestCode == MRZ_REQUEST) {

            if (resultCode == RESULT_OK) {

                String mrzElements = (String) data.getSerializableExtra(XavierActivity.MRZ_LINES);

                if (mrzElements == null || mrzElements.length() == 0) {
                    Log.e(TAG, "No MRZ lines..");

                } else {
                    Log.e(TAG, "" + mrzElements);
                    processMrzResult(mrzElements);
                }

            } else if (resultCode == RESULT_CANCELED) {

                String errorMessage = (String) data.getSerializableExtra(XavierActivity.ERROR_MESSAGE);
                if (errorMessage != null || !errorMessage.isEmpty()) {
                    Log.d(TAG, "error: " + errorMessage);
                }
            }

        } else if (requestCode == USER_DOCTYPE_SELECTION_REQUEST) {

            if (resultCode == RESULT_OK) {
                // After the user select "P" or "ID" from the Valid Document Types drop down selecion
                HashMap<String, String> mrzKeyValueMap = (HashMap<String, String>) data.getSerializableExtra(MRZ_KEY_VALUE_MAP);

                // Inflate the view base on the user selection
                Log.d(TAG, "infalte view value map");

            }
        }
    }


    private void processMrzResult(String mrzElements) {
        Log.d(TAG, "Received MRZ Result from Xavier Library: \n");
        Log.d(TAG, mrzElements);

        HashMap<String, String> mrzKeyValueMap = new HashMap<>();

        if (!mrzElements.contains("documentType")) {
            mrzKeyValueMap.put("barcode", mrzElements);
            mrzKeyValueMap.put("documentType", "B");

        } else {
            // Start parsing the MRZ string elements
            mrzElements = mrzElements.substring(1, mrzElements.length() - 1);
            mrzKeyValueMap = parseMrzString(mrzElements);
        }

        if (isValidDocumentType(mrzKeyValueMap)) {
            Log.d(TAG, "Infalte view. " + mrzKeyValueMap);

        } else {
            Log.d(TAG, "Document unknown");
            Toast.makeText(getContext(), "Unknown scanned object, please try again", Toast.LENGTH_SHORT);
        }

        inflateView(mrzKeyValueMap);
    }


    private boolean isValidDocumentType(HashMap<String, String> mrzKeyValueMap) {

        String docType = mrzKeyValueMap.get("documentType");
        if (docType != null) {
            if (ValidDocumentTypes.fromString(docType) == ValidDocumentTypes.UNKNOWN) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }


    public static HashMap<String, String> parseMrzString(String mrz) {
        Log.d(TAG, "+++ parseMrzString() +++");

        HashMap<String, String> mrzKeyValueMap = new HashMap<String, String>();

        Log.d(TAG, "==========================\n");

        StringTokenizer st = new StringTokenizer(mrz, "=,");
        while (st.hasMoreTokens()) {

            String key = st.nextToken();

            key = key.trim();

            String val = st.nextToken();
            if (val == null) {
                val = "";
            } else {
                val = val.replace("<", " ");
                val = val.trim();
            }

            mrzKeyValueMap.put(key, val);
            Log.d(TAG, "[" + key + "] = " + val);
        }

        Log.d(TAG, "==========================\n");
        return mrzKeyValueMap;
    }


    private void inflateView(HashMap<String, String> mrzKeyValueMap) {
        Log.d(TAG, "+++ inflateView() +++");
        String docType = mrzKeyValueMap.get("documentType");

        if (docType != null && docType.compareTo(PASSPORT_DOC_TYPE) == 0) {
            inflatePassportLayout(mrzKeyValueMap);
            Log.d(TAG, "inflate passport layout");
            goToNextFrag();
        }
    }


    private void inflatePassportLayout(final HashMap<String, String> mrzKeyValueMap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mrzKeyValueMap.get("number") != null) {
                    idNumber = mrzKeyValueMap.get("number");
                    Log.d(TAG, "id number: " + idNumber);
                }

                if (mrzKeyValueMap.get("name") != null) {
                    fullName = mrzKeyValueMap.get("name");
                    Log.d(TAG, "name: " + fullName);
                }

                if (mrzKeyValueMap.get("birthdate") != null) {
                    dob = mrzKeyValueMap.get("birthdate");
                    Log.d(TAG, "dob: " + dob);
                }

                if (mrzKeyValueMap.get("org") != null) {
                    pob = mrzKeyValueMap.get("org");
                    Log.d(TAG, "pob: " + mrzKeyValueMap.get("org"));
                }

                if (mrzKeyValueMap.get("nationality") != null) {
                    nationality = mrzKeyValueMap.get("nationality");
                    Log.d(TAG, "nationality: " + nationality);
                }

                if (mrzKeyValueMap.get("sex") != null) {
                    gender = mrzKeyValueMap.get("sex");
                    Log.d(TAG, "gender: " + gender);
                }

                ((RegUpMemberActivity) getActivity()).passportNo = idNumber;
                ((RegUpMemberActivity) getActivity()).fullName = fullName;
                ((RegUpMemberActivity) getActivity()).dob = dobFormatting(dob);
                ((RegUpMemberActivity) getActivity()).pob = pob;
                ((RegUpMemberActivity) getActivity()).nationality = nationality;
                ((RegUpMemberActivity) getActivity()).gender = gender;

                if (nationality.equals("SGP")) {
                    ((RegUpMemberActivity) getActivity()).isSingaporean = true;
                } else {
                    ((RegUpMemberActivity) getActivity()).isSingaporean = false;
                }
            }
        });
    }

    private String dobFormatting(String dobRaw) {
        String newFormat = "yyyy-MM-dd";
        DateFormat strToDateFormat = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
        try {
            //Convert to date
            Date convertedDate = strToDateFormat.parse(dobRaw);

            //Convert to string with format
            SimpleDateFormat dateToStrFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
            dateToStrFormat.applyPattern(newFormat);
            String convertedDateString = dateToStrFormat.format(convertedDate);

            return convertedDateString;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
