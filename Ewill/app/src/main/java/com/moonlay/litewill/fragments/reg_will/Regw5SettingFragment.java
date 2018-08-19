package com.moonlay.litewill.fragments.reg_will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.MyWill;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Regw5SettingFragment extends BaseFragment {
    private static final String TAG = "mydebug_regwill5";
    Switch swLfSearchable, swPaSearchable, swAllSearchable;
    CheckBox cbLfStatus, cbLfLocation, cbLfWill,
            cbPaStatus, cbPaLocation, cbPaWill,
            cbAllStatus, cbAllLocation, cbAllWill;
    Button btnNext;
    boolean lfSearchable = false, lfStatus = false, lfLocation = false, lfWill = false,
            paSearchable = false, paStatus = false, paLocation = false, paWill = false,
            allSearchable = false, allStatus = false, allLocation = false, allWill = false;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    public Regw5SettingFragment() {
        // Required empty public constructor
    }

    public static Regw5SettingFragment newInstance(String willName, Address[] addresses, ArrayList<Document> willDocument, ArrayList<Shared> willShared) {
        Regw5SettingFragment frag = new Regw5SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("will_name", willName);
        bundle.putSerializable("will_address", addresses);
        bundle.putSerializable("will_document", willDocument);
        bundle.putSerializable("will_shared", willShared);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg21_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getContext());

        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        swLfSearchable = mView.findViewById(R.id.sw_lf_searchable);
        cbLfStatus = mView.findViewById(R.id.cb_lf_status);
        cbLfLocation = mView.findViewById(R.id.cb_lf_location);
        cbLfWill = mView.findViewById(R.id.cb_lf_will);

        swPaSearchable = mView.findViewById(R.id.sw_pa_searchable);
        cbPaStatus = mView.findViewById(R.id.cb_pa_status);
        cbPaLocation = mView.findViewById(R.id.cb_pa_location);
        cbPaWill = mView.findViewById(R.id.cb_pa_will);

        swAllSearchable = mView.findViewById(R.id.sw_all_searchable);
        cbAllStatus = mView.findViewById(R.id.cb_all_status);
        cbAllLocation = mView.findViewById(R.id.cb_all_location);
        cbAllWill = mView.findViewById(R.id.cb_all_will);

        /*documents = new Document[((RegWillActivity) getActivity()).documents.size()];
        shareds = new Shared[((RegWillActivity) getActivity()).shareds.size()];
        addresses = new Address[((RegWillActivity) getActivity()).addresses.size()];

        documents = ((RegWillActivity) getActivity()).documents.toArray(documents);
        shareds = ((RegWillActivity) getActivity()).shareds.toArray(shareds);
        addresses = ((RegWillActivity) getActivity()).addresses.toArray(addresses);*/

        btnNext = mView.findViewById(R.id.btn_next);

        init();
    }

    private void init() {
        // LAW FIRM
        cbLfStatus.setEnabled(false);
        cbLfLocation.setEnabled(false);
        cbLfWill.setEnabled(false);

        swLfSearchable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lfSearchable = true;

                    cbLfStatus.setEnabled(true);

                } else {
                    lfSearchable = false;

                    cbLfStatus.setChecked(false);
                    cbLfStatus.setEnabled(false);
                    lfStatus = false;

                    cbLfLocation.setChecked(false);
                    cbLfLocation.setEnabled(false);
                    lfLocation = false;

                    cbLfWill.setChecked(false);
                    cbLfWill.setEnabled(false);
                    lfWill = false;
                }
                Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);
            }
        });

        cbLfStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lfStatus = true;
                    cbLfLocation.setEnabled(true);
                    cbLfWill.setEnabled(true);

                } else {
                    lfStatus = false;

                    cbLfLocation.setChecked(false);
                    cbLfLocation.setEnabled(false);
                    lfLocation = false;

                    cbLfWill.setChecked(false);
                    cbLfWill.setEnabled(false);
                    lfWill = false;
                }
                Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);
            }
        });

        cbLfWill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lfWill = true;
                } else {
                    lfWill = false;
                }
                Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);
            }
        });

        cbLfLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lfLocation = true;
                } else {
                    lfLocation = false;
                }
                Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);
            }
        });


        //PUBLIC AUTHORITIES
        cbPaStatus.setEnabled(false);
        cbPaLocation.setEnabled(false);
        cbPaWill.setEnabled(false);
        swPaSearchable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paSearchable = true;

                    cbPaStatus.setEnabled(true);

                } else {
                    paSearchable = false;

                    cbPaStatus.setChecked(false);
                    cbPaStatus.setEnabled(false);
                    paStatus = false;

                    cbPaLocation.setChecked(false);
                    cbPaLocation.setEnabled(false);
                    paLocation = false;

                    cbPaWill.setChecked(false);
                    cbPaWill.setEnabled(false);
                    paWill = false;
                }
                Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);
            }
        });

        cbPaStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paStatus = true;
                    cbPaLocation.setEnabled(true);
                    cbPaWill.setEnabled(true);

                } else {
                    paStatus = false;

                    cbPaLocation.setChecked(false);
                    cbPaLocation.setEnabled(false);
                    paLocation = false;

                    cbPaWill.setChecked(false);
                    cbPaWill.setEnabled(false);
                    paWill = false;
                }
                Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);
            }
        });

        cbPaLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paLocation = true;
                } else {
                    paLocation = false;
                }
                Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);
            }
        });

        cbPaWill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paWill = true;
                } else {
                    paWill = false;
                }
                Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);
            }
        });


        //ALL REGISTERED USERS
        cbAllStatus.setEnabled(false);
        cbAllLocation.setEnabled(false);
        cbAllWill.setEnabled(false);
        swAllSearchable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allSearchable = true;

                    cbAllStatus.setEnabled(true);

                } else {
                    allSearchable = false;

                    cbAllStatus.setChecked(false);
                    cbAllStatus.setEnabled(false);
                    allStatus = false;

                    cbAllLocation.setChecked(false);
                    cbAllLocation.setEnabled(false);
                    allLocation = false;

                    cbAllWill.setChecked(false);
                    cbAllWill.setEnabled(false);
                    allWill = false;
                }
                Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);
            }
        });

        cbAllStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allStatus = true;
                    cbAllLocation.setEnabled(true);
                    cbAllWill.setEnabled(true);

                } else {
                    allStatus = false;

                    cbAllLocation.setChecked(false);
                    cbAllLocation.setEnabled(false);
                    allLocation = false;

                    cbAllWill.setChecked(false);
                    cbAllWill.setEnabled(false);
                    allWill = false;
                }
                Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);
            }
        });

        cbAllLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allLocation = true;
                } else {
                    allLocation = false;
                }
                Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);
            }
        });

        cbAllWill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allWill = true;
                } else {
                    allWill = false;
                }
                Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String willName = getArguments().getString("will_name");
                Address[] addresses = (Address[]) getArguments().getSerializable("will_address");
                ArrayList<Document> willDocument = (ArrayList<Document>) getArguments().getSerializable("will_document");
                ArrayList<Shared> willShared = (ArrayList<Shared>) getArguments().getSerializable("will_shared");

                requestCreateWill(willName, addresses, willDocument, willShared);

                try {
                    /*Log.d(TAG, "-- Will Registration  Finish --");
                    Log.d(TAG, "LF Status: " + lfStatus);
                    Log.d(TAG, "LF Shared: " + lfSearchable);
                    Log.d(TAG, "LF Location: " + lfLocation);
                    Log.d(TAG, "LF Doc Shared: " + lfWill);
                    Log.d(TAG, "PA Status: " + paStatus);
                    Log.d(TAG, "PA Shared: " + paSearchable);
                    Log.d(TAG, "PA Location: " + paLocation);
                    Log.d(TAG, "PA Doc Shared: " + paWill);
                    Log.d(TAG, "User Status: " + allStatus);
                    Log.d(TAG, "User Shared: " + allSearchable);
                    Log.d(TAG, "User Location: " + allLocation);
                    Log.d(TAG, "User Doc Shared: " + allWill);
                    Log.d(TAG, "Shareds size: " + willShared.size());
                    Log.d(TAG, "Shareds 0: " + willShared.get(0).getEmail());*/
                    /*Log.d(TAG, "Addresses size: " + addresses.length);
                    Log.d(TAG, "Addresses 0 street: " + addresses[0].getStreet());*/
                    /*Log.d(TAG, "Documents size: " + documents.size());
                    Log.d(TAG, "Documents 0: " + documents.get(0.getDocumentPath());*/

                } catch (Exception e) {
                    Log.d(TAG, "Exception: " + e);
                }
            }
        });
    }


    private void requestCreateWill(String willName, Address[] addresses, ArrayList<Document> documents, ArrayList<Shared> shareds) {
        final String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        final Address[] willAddr = new Address[1];
        willAddr[0] = new Address("street test", "city test", "country test", "zipcode test", 106.808717, -6.226081);

        Call<StandardResponse> call = apiInterface.createWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new MyWill(willName,
                        lfStatus, lfSearchable, lfLocation, lfWill,
                        paStatus, paSearchable, paLocation, paWill,
                        allStatus, allSearchable, allLocation, allWill,
                        documents,
                        shareds,
                        addresses));

        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);

                    /*Log.d(TAG, "Addresses size: " + willAddr.length);
                    Log.d(TAG, "Addresses 0 street: " + willAddr[0].getStreet());*/
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
            }
        });
    }
}