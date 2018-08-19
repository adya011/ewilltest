package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpdateWill;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillDetailSearchableFragment extends BaseFragment {
    private static final String TAG = "mydebug_updsrc";

    Button btnUpdate;
    Switch swLfSearchable, swPaSearchable, swAllSearchable;
    CheckBox cbLfStatus, cbLfLocation, cbLfWill,
            cbPaStatus, cbPaLocation, cbPaWill,
            cbAllStatus, cbAllLocation, cbAllWill;

    String willName;
    ArrayList<Shared> shareds = new ArrayList<>();

    boolean lfSearchable = false, lfStatus = false, lfLocation = false, lfWill = false,
            paSearchable = false, paStatus = false, paLocation = false, paWill = false,
            allSearchable = false, allStatus = false, allLocation = false, allWill = false;

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    public UpdWillDetailSearchableFragment() {
        // Required empty public constructor
    }

    public static UpdWillDetailSearchableFragment
    newInstance(int willId, String willName,
                boolean lfSearchable, boolean lfStatus, boolean lfLocation, boolean lfWill,
                boolean paSearchable, boolean paStatus, boolean paLocation, boolean paWill,
                boolean allSearchable, boolean allStatus, boolean allLocation, boolean allWill,
                ArrayList<Shared> shareds) {
        UpdWillDetailSearchableFragment frag = new UpdWillDetailSearchableFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("will_id", willId);

        bundle.putString("will_name", willName);

        bundle.putBoolean("lf_searchable", lfSearchable);
        bundle.putBoolean("lf_status", lfStatus);
        bundle.putBoolean("lf_location", lfLocation);
        bundle.putBoolean("lf_will", lfWill);

        bundle.putBoolean("pa_searchable", paSearchable);
        bundle.putBoolean("pa_status", paStatus);
        bundle.putBoolean("pa_location", paLocation);
        bundle.putBoolean("pa_will", paWill);

        bundle.putBoolean("all_searchable", allSearchable);
        bundle.putBoolean("all_status", allStatus);
        bundle.putBoolean("all_location", allLocation);
        bundle.putBoolean("all_will", allWill);

        bundle.putSerializable("shareds", shareds);

        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        btnUpdate = mView.findViewById(R.id.btn_next);
        btnUpdate.setText("Update");

        ((WillActivity) getActivity()).setActionBarTitle("Will Searchable");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        checkboxSwitchListener();

        final int willId = getArguments().getInt("will_id");

        willName = getArguments().getString("will_name");

        lfSearchable = getArguments().getBoolean("lf_searchable");
        lfStatus = getArguments().getBoolean("lf_status");
        lfLocation = getArguments().getBoolean("lf_location");
        lfWill = getArguments().getBoolean("lf_will");

        paSearchable = getArguments().getBoolean("pa_searchable");
        paStatus = getArguments().getBoolean("pa_status");
        paLocation = getArguments().getBoolean("pa_location");
        paWill = getArguments().getBoolean("pa_will");

        allSearchable = getArguments().getBoolean("all_searchable");
        allStatus = getArguments().getBoolean("all_status");
        allLocation = getArguments().getBoolean("all_location");
        allWill = getArguments().getBoolean("all_will");

        shareds = (ArrayList<Shared>) getArguments().getSerializable("shareds");

        Log.d(TAG, "== Will Searchable ==");

        Log.d(TAG, "lfSearchable: " + lfSearchable);
        Log.d(TAG, "lfStatus: " + lfStatus);
        Log.d(TAG, "lfLocation: " + lfLocation);
        Log.d(TAG, "lfWill: " + lfWill);

        Log.d(TAG, "paSearchable: " + paSearchable);
        Log.d(TAG, "paStatus: " + paStatus);
        Log.d(TAG, "paLocation: " + paLocation);
        Log.d(TAG, "paWill: " + paWill);

        Log.d(TAG, "allSearchable: " + allSearchable);
        Log.d(TAG, "allStatus: " + allStatus);
        Log.d(TAG, "allLocation: " + allLocation);
        Log.d(TAG, "allWill: " + allWill);

        swLfSearchable.setChecked(lfSearchable);
        cbLfStatus.setChecked(lfStatus);
        cbLfLocation.setChecked(lfLocation);
        cbLfWill.setChecked(lfWill);

        swPaSearchable.setChecked(paSearchable);
        cbPaStatus.setChecked(paStatus);
        cbPaLocation.setChecked(paLocation);
        cbPaWill.setChecked(paWill);

        swAllSearchable.setChecked(allSearchable);
        cbAllStatus.setChecked(allStatus);
        cbAllLocation.setChecked(allLocation);
        cbAllWill.setChecked(allWill);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = sharedPrefManager.getLoginToken();
                String uniqueID = UUID.randomUUID().toString();

                Call<StandardResponse> call = apiInterface.updateWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                        new UpdateWill(willId, willName,
                                lfSearchable, lfStatus, lfLocation, lfWill,
                                paSearchable, paStatus, paLocation, paWill,
                                allSearchable, allStatus, allLocation, allWill));

                Log.d(TAG,
                        "lfSearchable: " + lfSearchable + ", lfStatus: " + lfStatus + ", lfLocation: " + lfLocation + ", lfWill: " + lfWill +
                                ", paSearchable: " + paSearchable + ", paStatus: " + paStatus + ", paLocation: " + paLocation + ", paWill: " + paWill +
                                ", allSearchable" + allSearchable + ", allStatus" + allStatus + ", allLocation: " + allLocation + ", allLocation: " + allLocation + ", allWill: " + allWill);

                call.enqueue(new Callback<StandardResponse>() {
                    @Override
                    public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                        Log.d(TAG, "Will Update: API response code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());

                        if (response.code() != 200) {
                            Log.d(TAG, "url: " + response.raw().request().url().toString());

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_will, WillNotifyFragment.newInstance(shareds, Constants.WILL_UPDATED));
                            ft.commit();
                        }

                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }

                    @Override
                    public void onFailure(Call<StandardResponse> call, Throwable t) {
                        Log.d(TAG, "Failed");
                    }
                });
            }
        });
    }

    private void checkboxSwitchListener() {
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
                /*Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);*/
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
                /*Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);*/
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
                /*Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);*/
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
                /*Log.d(TAG, "lf searchable: " + lfSearchable);
                Log.d(TAG, "lf status: " + lfStatus);
                Log.d(TAG, "lf location: " + lfLocation);
                Log.d(TAG, "lf will: " + lfWill);*/
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
                /*Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);*/
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
                /*Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);*/
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
                /*Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);*/
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
                /*Log.d(TAG, "pa searchable: " + paSearchable);
                Log.d(TAG, "pa status: " + paStatus);
                Log.d(TAG, "pa location: " + paLocation);
                Log.d(TAG, "pa will: " + paWill);*/
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
                /*Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);*/
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
                /*Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);*/
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
                /*Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);*/
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
                /*Log.d(TAG, "all searchable: " + allSearchable);
                Log.d(TAG, "all status: " + allStatus);
                Log.d(TAG, "all location: " + allLocation);
                Log.d(TAG, "all will: " + allWill);*/
            }
        });
    }
}
