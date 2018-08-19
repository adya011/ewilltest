package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenWillDetailFragment extends BaseFragment {
    private static final String TAG = "mydebug_openwilldet";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    TextView tvFullName, tvWillName, tvWillLocation;
    ImageView imageMap;
    RelativeLayout layDoc;

    int willId;
    String fullName, willName, willLocation;
    ArrayList<Address> willLocations;
    ArrayList<Document> willDocuments = new ArrayList<>();

    public OpenWillDetailFragment() {
        // Required empty public constructor
    }

    public static OpenWillDetailFragment newInstance(int getWillId) {
        OpenWillDetailFragment frag = new OpenWillDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", getWillId);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_will_detail_open, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFullName = mView.findViewById(R.id.tv_full_name);
        tvWillName = mView.findViewById(R.id.tv_will_name);
        tvWillLocation = mView.findViewById(R.id.tv_will_location);
        imageMap = mView.findViewById(R.id.map_image);
        layDoc = mView.findViewById(R.id.lay_will_doc);

        init();
    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        willId = getArguments().getInt("will_id");

        //requestOpenWillDetail();

        layDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_tab1_layout, UpdWillDetailDocFragment.newInstance(willDocuments));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    /*private void requestOpenWillDetail() {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<MyWillDetailResponse> call = apiInterface.myWillDetail(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION,
                token, willId);

        call.enqueue(new Callback<MyWillDetailResponse>() {
            @Override
            public void onResponse(Call<MyWillDetailResponse> call, Response<MyWillDetailResponse> response) {
                Log.d(TAG, "Will Detail: API Response. Code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());

                if (response.code() == 200 && response.isSuccessful()) {
                    String street = "";
                    String city = "";
                    String zipCode = "";
                    String country = "";

                    fullName = response.body().getResult()[0].getFullName();
                    tvFullName.setText(fullName);

                    willName = response.body().getResult()[0].getName();
                    tvWillName.setText(willName);

                    willLocations = response.body().getResult()[0].getAddresses();

                    if (willLocations.get(0).getStreet() != null) {
                        street = willLocations.get(0).getStreet().toString();
                    }

                    if (willLocations.get(0).getCity() != null) {
                        city = willLocations.get(0).getCity().toString();
                    }

                    if (willLocations.get(0).getZipCode() != null) {
                        zipCode = willLocations.get(0).getZipCode().toString();
                    }

                    if (willLocations.get(0).getCountry() != null) {
                        country = willLocations.get(0).getCountry().toString();
                    }

                    willLocation = street + " " + city + " " + zipCode + " " + country;
                    tvWillLocation.setText(willLocation);

                    Picasso.with(getContext())
                            .load("http://maps.google.com/maps/api/staticmap?center="
                                    + willLocations.get(0).getLatitude() + ","
                                    + willLocations.get(0).getLongitude()
                                    + "&zoom=15&size=350x200&sensor=false"
                                    + "&markers="
                                    + willLocations.get(0).getLatitude() + ","
                                    + willLocations.get(0).getLongitude())
                            .into(imageMap);

                    willDocuments.addAll(response.body().getResult()[0].getDocuments());

                    if(willDocuments.size() < 1){
                        layDoc.setVisibility(RelativeLayout.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyWillDetailResponse> call, Throwable t) {

            }
        });
    }*/
}
