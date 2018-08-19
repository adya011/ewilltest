package com.moonlay.litewill.dialogfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.RegWillActivity;
import com.moonlay.litewill.fragments.reg_will.Regw4NotifyFragment;
import com.moonlay.litewill.fragments.will.NoWillNotifyFragment;
import com.moonlay.litewill.model.Shared;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoWillNotifyEditSharedDialog extends DialogFragment {
    Button btnSave, btnCancel;
    ImageButton btnDelete;
    TextInputEditText etEmail;
    CheckBox cbLocation, cbWill;
    boolean isWillShared, isLocationShared;

    int itemIndex;
    String sharedEmail;
    boolean sharedDoc;
    boolean sharedLoc;

    public NoWillNotifyEditSharedDialog() {
        // Required empty public constructor
    }

    public static NoWillNotifyEditSharedDialog newInstance(int itemIndex, String sharedEmail,
                                                           boolean sharedLoc, boolean sharedDoc){
        NoWillNotifyEditSharedDialog frag = new NoWillNotifyEditSharedDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("index", itemIndex);
        bundle.putString("shared_email", sharedEmail);
        bundle.putBoolean("shared_loc", sharedLoc);
        bundle.putBoolean("shared_doc", sharedDoc);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify_people, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel = view.findViewById(R.id.btn_no);
        btnSave = view.findViewById(R.id.btn_yes);
        cbLocation = view.findViewById(R.id.cb_location);
        cbWill = view.findViewById(R.id.cb_will);
        etEmail = view.findViewById(R.id.et_email);
        btnDelete = view.findViewById(R.id.btn_delete);

        init();
    }

    private void init() {
        itemIndex = getArguments().getInt("index");
        sharedEmail = getArguments().getString("shared_email");
        sharedDoc = getArguments().getBoolean("shared_doc");
        sharedLoc = getArguments().getBoolean("shared_loc");

        etEmail.setText(sharedEmail);
        cbWill.setChecked(sharedDoc);
        cbLocation.setChecked(sharedLoc);

        cbLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isLocationShared = true;
                } else {
                    isLocationShared = false;
                }
            }
        });

        cbWill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isWillShared = true;
                } else {
                    isWillShared = false;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                NoWillNotifyFragment frag = (NoWillNotifyFragment) getTargetFragment();
                frag.shareds.set(itemIndex, new Shared(email, isWillShared, isLocationShared));

                getDialog().dismiss();

                frag.refreshAdapter();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoWillNotifyFragment frag = (NoWillNotifyFragment) getTargetFragment();
                frag.shareds.remove(itemIndex);

                getDialog().dismiss();

                frag.refreshAdapter();
            }
        });
    }
}
