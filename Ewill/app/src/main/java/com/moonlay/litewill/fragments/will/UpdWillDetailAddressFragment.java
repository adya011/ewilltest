package com.moonlay.litewill.fragments.will;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.adapter.PlaceAutocompleteAdapter;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpdateWillAddress;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillDetailAddressFragment extends BaseFragment
        implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    public static String TAG = "mydebug_updaddr";
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Button btnNext;
    EditText etAddress;
    AutoCompleteTextView etSearchBar;

    int willId;
    String addrStreet;
    String addrCity;
    String addrCountry;
    String addrZipCode;
    double addrLng;
    double addrLat;
    ArrayList<Shared> shareds = new ArrayList<>();

    public static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, 168),
            new LatLng(71, 136));

    private GoogleMap map;
    private Boolean locationPermissionGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient googleApiClient;

    public UpdWillDetailAddressFragment() {
        // Required empty public constructor
    }

    public static UpdWillDetailAddressFragment
    newInstance(int addressId, String street, String city, String country, String zipCode, double longitude, double latitude,
                ArrayList<Shared> shareds) {
        UpdWillDetailAddressFragment frag = new UpdWillDetailAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("address_id", addressId);
        bundle.putString("street", street);
        bundle.putString("city", city);
        bundle.putString("country", country);
        bundle.putString("zipCode", zipCode);
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("latitude", latitude);
        bundle.putSerializable("shareds", shareds);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg18_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        etSearchBar = mView.findViewById(R.id.et_search_bar);
        btnNext = mView.findViewById(R.id.btn_next);

        etAddress = mView.findViewById(R.id.et_address);

        getLocationPermission();
        ((WillActivity) getActivity()).setActionBarTitle("Will Address");
        ((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        checkMapService();

        willId = getArguments().getInt("address_id");
        addrStreet = getArguments().getString("street");
        addrCity = getArguments().getString("city");
        addrCountry = getArguments().getString("country");
        addrZipCode = getArguments().getString("zipCode");
        addrLng = getArguments().getDouble("longitude");
        addrLat = getArguments().getDouble("latitude");
        shareds = (ArrayList<Shared>) getArguments().getSerializable("shareds");

        Log.d(TAG, "++ init ++");
        Log.d(TAG, "will id: " + willId);
        Log.d(TAG, "street: " + addrStreet);
        Log.d(TAG, "city: " + addrCity);
        Log.d(TAG, "country: " + addrCountry);
        Log.d(TAG, "zipcode: " + addrZipCode);
        Log.d(TAG, "long: " + addrLng);
        Log.d(TAG, "lat:" + addrLat);

        etAddress.setText(addrStreet);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpdateAddress();
            }
        });
    }

    private void requestUpdateAddress(){
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();
        String addressStreet = etAddress.getText().toString();

        Call<StandardResponse> call = apiInterface.updateWillAddress(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                new UpdateWillAddress(willId, addressStreet, addrCity, addrCountry, addrZipCode, addrLng, addrLat));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                Log.d(TAG, "Will Update Address: API response code: " + response.code() + ". isSuccessfull: " + response.isSuccessful());

                if (response.code() == 200 && response.isSuccessful()) {
                    /*FragmentManager fm = getFragmentManager();
                    fm.popBackStack();*/

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_will, WillNotifyFragment.newInstance(shareds, Constants.WILL_UPDATED));
                    ft.commit();

                    Log.d(TAG, "will id: " + willId);
                    Log.d(TAG, "will street: " + addrStreet);
                    Log.d(TAG, "will city: " + addrCity);
                    Log.d(TAG, "will country: " + addrCountry);
                    Log.d(TAG, "will zip code: " + addrZipCode);
                    Log.d(TAG, "will long: " + addrLng);
                    Log.d(TAG, "will lat: " + addrLat);

                } else {
                    Log.d(TAG, "will id: " + willId);
                    Log.d(TAG, "will street: " + addrStreet);
                    Log.d(TAG, "will city: " + addrCity);
                    Log.d(TAG, "will country: " + addrCountry);
                    Log.d(TAG, "will zip code: " + addrZipCode);
                    Log.d(TAG, "will long: " + addrLng);
                    Log.d(TAG, "will lat: " + addrLat);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
            }
        });
    }


    public boolean checkMapService() {
        Log.d(TAG, "=== checkMapService ===");

        int avail = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if (avail == ConnectionResult.SUCCESS) {
            Log.d(TAG, "GooglePlay services is working");
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(avail)) {
            Log.d(TAG, "GooglePlay service error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), avail, ERROR_DIALOG_REQUEST);
            dialog.show();

        } else {
            Toast.makeText(getContext(), "Map service error", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    // ======== GET PERMISSION ========
    public void getLocationPermission() {
        Log.d(TAG, "+++ getLocationPermission +++");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Check self permission (Fine Location)");
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                Log.d(TAG, "Check self permission (Course Location), Location permission granted");
                initMap();

            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "Check self permission (Location Permission) Request Code");

            }
        } else {
            Log.d(TAG, "Check self permission FAILED");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "+++ onRequestPermissionResult +++");
        locationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            Log.d(TAG, "Location permission failed");
                            return;
                        }
                    }

                    Log.d(TAG, "Location permission granted");
                    locationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }


    private void initMap() {
        Log.d(TAG, "+++ initMap +++");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "+++ onMapReady +++");
        map = googleMap;
        if (locationPermissionGranted) {
            Log.d(TAG, "Permission granted");
            getStartLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }
        moveCamera(new LatLng(addrLat, addrLng), DEFAULT_ZOOM, "My Location");
        initPlaces();
    }


    private void getStartLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (locationPermissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Geocoder geocoder = new Geocoder(getActivity());
                            List<Address> addresses = new ArrayList<>();

                            try {
                                addresses = geocoder.getFromLocation(addrLat, addrLng, 1);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            moveCamera(new LatLng(addrLat, addrLng), DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "Current location is null");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Get Device Location Error");
        }
    }


    private void initPlaces() {
        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        etSearchBar.setOnItemClickListener(autoCompleteClickListener);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(),
                Places.getGeoDataClient(getActivity(), null), LAT_LNG_BOUNDS, null);

        etSearchBar.setAdapter(placeAutocompleteAdapter);
        etSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();

                }
                return false;
            }
        });
    }


    // get map position with address
    private void geoLocate() {
        Log.d(TAG, "geoLocate");
        String searchString = etSearchBar.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocation Error: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate found a location: " + address.toString());

            addrLat = address.getLatitude();
            addrLng = address.getLongitude();

            if (address.getAddressLine(0) != null) {
                addrStreet = address.getAddressLine(0).toString();
                etAddress.setText(addrStreet);

            } else {
                addrStreet = "";
            }

            if (address.getLocality() != null) {
                addrCity = address.getLocality().toString();
            } else {
                addrCity = "";
            }

            if (address.getCountryName() != null) {
                addrCountry = address.getCountryName().toString();
            } else {
                addrCountry = "";
            }

            if (address.getPostalCode() != null) {
                addrZipCode = address.getPostalCode().toString();
            } else {
                addrZipCode = "";
            }

            moveCamera(new LatLng(addrLat, addrLng), DEFAULT_ZOOM, "My Location");

            Log.d(TAG, "++ Geo Locate ++");
            Log.d(TAG, "latitude: " + address.getLatitude());
            Log.d(TAG, "longitude: " + address.getLongitude());
            Log.d(TAG, "street: " + address.getAddressLine(0));
            Log.d(TAG, "city: " + address.getLocality());
            Log.d(TAG, "zipcode: " + address.getPostalCode());
            Log.d(TAG, "country: " + address.getCountryName());
        }
    }


    // Move camera
    private void moveCamera(LatLng latlng, float zoom) {
        Log.d(TAG, "Move Camera: moving camera to: lat: " + latlng.latitude + ", long: " + latlng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }


    // Move camera + drop marker
    private void moveCamera(LatLng latlng, float zoom, String title) {
        Log.d(TAG, "Move Camera: moving camera to: lat: " + latlng.latitude + ", long: " + latlng.longitude);
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latlng)
                .title(title);
        map.addMarker(options);
    }


    private AdapterView.OnItemClickListener autoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);

            //placeResult.setResultCallback(updatePlaceDetailCallback);
        }
    };


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Pause");
        if (locationPermissionGranted) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }
}
