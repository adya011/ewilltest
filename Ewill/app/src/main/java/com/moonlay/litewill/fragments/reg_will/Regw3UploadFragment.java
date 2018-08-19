package com.moonlay.litewill.fragments.reg_will;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.adapter.MyWillDetailDocAdapter;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.Document;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Regw3UploadFragment extends BaseFragment {
    Button btnNext, btnWillUpload;
    RecyclerView recyclerView;
    MyWillDetailDocAdapter adapter;
    ArrayList<Document> willDocument = new ArrayList<>();

    private static String TAG = "mydebug_regwill3";
    private static int RESULT_LOAD_IMG = 1;
    String filePath;
    String fileName;

    public Regw3UploadFragment() {
        // Required empty public constructor
    }

    public static Regw3UploadFragment newInstance(String willName, Address[] willAddresses) {
        Regw3UploadFragment frag = new Regw3UploadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("will_name", willName);
        bundle.putSerializable("will_address", willAddresses);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg19_upload, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        btnWillUpload = mView.findViewById(R.id.btn_willupload);
        recyclerView = mView.findViewById(R.id.recycler_view);

        initView();
    }

    private void initView() {
        adapter = new MyWillDetailDocAdapter(willDocument, R.layout.list_will_upload, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnWillUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPdfIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openPdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                openPdfIntent.setType("application/pdf");
                startActivityForResult(openPdfIntent, 7);
                //btnWillUpload.setEnabled(false);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String willName = getArguments().getString("will_name");
                Address[] addresses = (Address[]) getArguments().getSerializable("will_address");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, Regw4NotifyFragment.newInstance(willName, addresses, willDocument));
                ft.addToBackStack(null);
                ft.commit();

                try {
                    Log.d(TAG, "-- Go to regw 4 --");
                    Log.d(TAG, "Will name: " + willName);
                    //Log.d(TAG, "Addresses size: " + addresses.length);
                    //Log.d(TAG, "Addresses 0 street: " + addresses[0].getStreet());
                    /*Log.d(TAG, "Documents size: " + willDocument.size());
                    Log.d(TAG, "Documents 0: " + willDocument.get(0).getDocumentRemark());*/

                } catch (Exception e) {
                    Log.d(TAG, "Exception: " + e);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "open pdf galery");
        Log.d(TAG, "Request code: " + requestCode);
        Log.d(TAG, "Result code: " + resultCode);

        String displayName = null;
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d(TAG, "display name:" + displayName);
                                willDocument.add(new Document(displayName, path));
                                refreshAddAdapter();
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d(TAG, "display name:" + displayName);
                    }
                }
                break;
        }
    }

    public void enableButtonUpload(){
        btnWillUpload.setEnabled(true);
    }

    private void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void refreshAddAdapter() {
        adapter.notifyItemInserted(1);
    }
}
