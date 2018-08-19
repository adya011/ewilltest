package com.moonlay.litewill;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import xavier.blacksharktech.com.xavierlib.XavierActivity;

import static com.moonlay.litewill.R.raw.xavier;

import xmlwise.Plist;

public class OCRActivity extends AppCompatActivity {
    public static final String TAG = "mydebug_ocr";
    public static final String MRZ_KEY_VALUE_MAP = "MrzKeyValueMap";
    public static final String PASSPORT_DOC_TYPE = "P";
    public static final int MRZ_REQUEST = 1;
    public static final int USER_DOCTYPE_SELECTION_REQUEST = 2;

    private EditText etName;
    public String idNumber;
    public String fullName;
    public String gender;
    public String dob;
    public String pob;
    public String nationality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ocr);
        //init();
    }

    public void init() {
        Log.d(TAG, "ocr start");
        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_ocr, new Reg3PassportFragment());
        ft.commit();*/

        startXavierActivity();
    }

    public void startXavierActivity() {
        Intent intent = new Intent(OCRActivity.this, XavierActivity.class);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        }
    }


    private void inflatePassportLayout(final HashMap<String, String> mrzKeyValueMap) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //inflateView(R.layout.passport_layout_inflater);

                if (mrzKeyValueMap.get("number") != null) {
                    idNumber = String.valueOf(mrzKeyValueMap.get("number"));
                    Log.d(TAG, "id number: " + idNumber);
                }

                if (mrzKeyValueMap.get("name") != null) {
                    fullName = String.valueOf(mrzKeyValueMap.get("name"));
                    Log.d(TAG, "name: " + fullName);
                }

                if (mrzKeyValueMap.get("birthdate") != null) {
                    dob = String.valueOf(mrzKeyValueMap.get("birthdate"));
                    Log.d(TAG, "dob: " + dob);
                }

                if (mrzKeyValueMap.get("org") != null) {
                    Log.d(TAG, "pob: " + mrzKeyValueMap.get("org"));
                }

                if (mrzKeyValueMap.get("nationality") != null) {
                    nationality = String.valueOf(mrzKeyValueMap.get("nationality"));
                    Log.d(TAG, "nationality: " + nationality);
                }

                if (mrzKeyValueMap.get("sex") != null) {
                    gender = String.valueOf(mrzKeyValueMap.get("sex"));
                    Log.d(TAG, "gender: " + gender);
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //ft.replace(R.id.content_ocr, Reg3PassportFragment.newInstance(idNumber, fullName, dob, pob, nationality, gender));
                ft.commit();

                Log.d(TAG, "get data: id number: " + idNumber + " full name: " + fullName);
            }
        });
    }

}
