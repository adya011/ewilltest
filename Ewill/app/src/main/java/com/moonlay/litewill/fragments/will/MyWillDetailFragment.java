package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.DeleteWill;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.MyWillDetailResponse;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWillDetailFragment extends BaseFragment {
    private static final String TAG = "mydebug_willdet";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    TextView tvWillName, tvWillAddress;
    Button btnDelete;
    ImageView imageMap;
    RelativeLayout laySearchable, layAddress, layShared, layDoc;

    int willId;
    String willName;
    String willAddress;
    boolean lfSearchable = false, lfStatus = false, lfLocation = false, lfWill = false,
            paSearchable = false, paStatus = false, paLocation = false, paWill = false,
            allSearchable = false, allStatus = false, allLocation = false, allWill = false;

    int addressId;
    String addrSreet;
    String addrCity;
    String addrCountry;
    String addrZipCode;
    double addrLng;
    double addrLat;
    Address[] addresses;
    ArrayList<Document> documents = new ArrayList<>();
    ArrayList<Shared> shareds = new ArrayList<>();

    public static MyWillDetailFragment newInstance(int getWillId) {
        MyWillDetailFragment frag = new MyWillDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", getWillId);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create");
        Log.d(TAG, "shared size: " + shareds.size());
        Log.d(TAG, "document size: " + documents.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_will_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "on View Created");

        tvWillName = mView.findViewById(R.id.tv_will_name);
        tvWillAddress = mView.findViewById(R.id.tv_will_address);
        imageMap = mView.findViewById(R.id.map_image);
        btnDelete = mView.findViewById(R.id.btn_delete);
        laySearchable = mView.findViewById(R.id.lay_will_search);
        layAddress = mView.findViewById(R.id.lay_address);
        layShared = mView.findViewById(R.id.lay_will_share);
        layDoc = mView.findViewById(R.id.lay_will_doc);

        ((WillActivity) getActivity()).setActionBarTitle("Will Detail");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        willId = getArguments().getInt("will_id");
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        requestWillDetail();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWill();
                //getActivity().finish();
            }
        });

        tvWillName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, UpdWillNameFragment.newInstance(willId, willName, shareds));
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        laySearchable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, UpdWillDetailSearchableFragment.newInstance(willId, willName,
                        lfSearchable, lfStatus, lfLocation, lfWill,
                        paSearchable, paStatus, paLocation, paWill,
                        allSearchable, allStatus, allLocation, allWill, shareds));
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        layAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, UpdWillDetailAddressFragment.newInstance(addressId,
                        addrSreet, addrCity, addrCountry, addrZipCode, addrLng, addrLat, shareds));
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        layShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, UpdWillDetailSharedFragment.newInstance(willId, shareds));
                ft.addToBackStack(null);
                ft.commit();

                Log.d(TAG, "++ Go to shared ++");
            }
        });

        layDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, UpdWillDetailDocFragment.newInstance(documents));
                ft.addToBackStack(null);
                ft.commit();

                Log.d(TAG, "++ Go to documents ++");
            }
        });
    }

    private void requestWillDetail() {
        final String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<MyWillDetailResponse> call = apiInterface.myWillDetail(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, willId);
        call.enqueue(new Callback<MyWillDetailResponse>() {
            @Override
            public void onResponse(Call<MyWillDetailResponse> call, Response<MyWillDetailResponse> response) {
                Log.d(TAG, "Will Detail: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());

                if (response.code() == 200 && response.isSuccessful() && response.body() != null) {
                    try {
                        willName = response.body().getResult()[0].getName();
                        Log.d(TAG, "Will Detail Name: " + response.body().getResult()[0].getName());
                        tvWillName.setText(willName);

                        addresses = response.body().getResult()[0].getWillAddresses();
                        willAddress = addresses[0].getStreet() + " " + addresses[0].getCity() + " " + addresses[0].getZipCode() + " " + addresses[0].getCountry();
                        tvWillAddress.setText(willAddress);
                        Log.d(TAG, "Will Detail Address Detail: " + willAddress);

                        Picasso.with(getContext())
                                .load("http://maps.google.com/maps/api/staticmap?center="
                                        + addresses[0].getLatitude() + ","
                                        + addresses[0].getLongitude()
                                        + "&zoom=15&size=350x200&sensor=false"
                                        + "&markers="
                                        + addresses[0].getLatitude() + ","
                                        + addresses[0].getLongitude())
                                .into(imageMap);

                        lfSearchable = response.body().getResult()[0].isLFShared();
                        lfStatus = response.body().getResult()[0].isLFStatus();
                        lfLocation = response.body().getResult()[0].isLFLocationShared();
                        lfWill = response.body().getResult()[0].isLFDocumentShared();

                        paSearchable = response.body().getResult()[0].isPAShared();
                        paStatus = response.body().getResult()[0].isPAStatus();
                        paLocation = response.body().getResult()[0].isPALocationShared();
                        paWill = response.body().getResult()[0].isPADocumentShared();

                        allSearchable = response.body().getResult()[0].isUserShared();
                        allStatus = response.body().getResult()[0].isUserStatus();
                        allLocation = response.body().getResult()[0].isUserLocationShared();
                        allWill = response.body().getResult()[0].isUserDocumentShared();

                        addressId = addresses[0].getId();
                        addrSreet = addresses[0].getStreet();
                        addrCity = addresses[0].getCity();
                        addrZipCode = addresses[0].getZipCode();
                        addrCountry = addresses[0].getCountry();
                        addrLat = addresses[0].getLatitude();
                        addrLng = addresses[0].getLongitude();

                        documents.addAll(response.body().getResult()[0].getDocuments());
                        shareds.addAll(response.body().getResult()[0].getShareds());

                        Log.d(TAG, "shared 0: " + response.body().getResult().length);

                    } catch (Exception e) {
                        Log.d(TAG, "Exception" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyWillDetailResponse> call, Throwable t) {
                Log.d(TAG, "Failed >> " + t);
            }
        });
    }

    private void deleteWill() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<StandardResponse> call = apiInterface.deleteWill(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, new DeleteWill(willId));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Delete Will: API Response.. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());
                if (response.code() == 200 && response.isSuccessful()) {
                    /*FragmentManager fm = getFragmentManager();
                    fm.popBackStack();*/

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_will, WillNotifyFragment.newInstance(shareds, Constants.WILL_DELETED));
                    ft.commit();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed: " + t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shareds.clear();
        documents.clear();
    }
}
