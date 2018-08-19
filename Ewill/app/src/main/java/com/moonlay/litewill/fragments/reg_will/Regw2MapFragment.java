package com.moonlay.litewill.fragments.reg_will;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
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
import com.moonlay.litewill.adapter.PlaceAutocompleteAdapter;
import com.moonlay.litewill.fragments.BaseFragment;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.utility.MyUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Regw2MapFragment extends BaseFragment
        implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "mydebug_regwill2";
    public static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, 168),
            new LatLng(71, 136));

    private AutoCompleteTextView searchBar;
    private Boolean locationPermissionGranted = false;
    private MapView mapView;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient googleApiClient;

    private String street = "";
    private String city = "";
    private String country = "";
    private String zipCode = "";
    private double longitude = 0;
    private double latitude = 0;

    Button btnNext;
    EditText etAddress;

    public Regw2MapFragment() {
        // Required empty public constructor
    }

    public static Regw2MapFragment newInstance(String willName) {
        Regw2MapFragment frag = new Regw2MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("will_name", willName);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }

        try {
            mView = inflater.inflate(R.layout.fragment_reg18_map, container, false);
        } catch (InflateException e) {

        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        searchBar = mView.findViewById(R.id.et_search_bar);
        etAddress = mView.findViewById(R.id.et_address);

        getLocationPermission();
        init();
    }

    private void init() {
        checkMapService();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addressStreet = etAddress.getText().toString();
                String willName = getArguments().getString("will_name");
                Address[] addresses = new Address[1];
                addresses[0] = new Address(addressStreet, city, country, zipCode, longitude, latitude);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, Regw3UploadFragment.newInstance(willName, addresses));
                ft.addToBackStack("reg18");
                ft.commit();

                try {
                    Log.d(TAG, "-- Go to regw 3 --");
                    Log.d(TAG, "Will name: " + willName);
                    Log.d(TAG, "Addresses size: " + addresses.length);
                    Log.d(TAG, "Addresses 0 street: " + addresses[0].getStreet());
                } catch (Exception e) {
                    Log.d(TAG, "Exception " + e);
                }
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
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }

        initPlaces();
    }


    private void getDeviceLocation() {
        Log.d(TAG, "+++ getDeviceLocation +++");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (locationPermissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Geocoder geocoder = new Geocoder(getActivity());
                            List<android.location.Address> addresses = new ArrayList<>();
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                latitude = currentLocation.getLatitude();
                                longitude = currentLocation.getLongitude();

                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    street = addresses.get(0).getAddressLine(0).toString();
                                } catch (Exception e) {
                                    Log.d(TAG, "Street not found");
                                }

                                try {
                                    city = addresses.get(0).getLocality().toString();
                                } catch (Exception e) {
                                    Log.d(TAG, "City not found");
                                }

                                /*String state = addresses.get(0).getAdminArea().toString();*/

                                try {
                                    zipCode = addresses.get(0).getPostalCode().toString();
                                } catch (Exception e) {
                                    Log.d(TAG, "Zip Code not found");
                                }

                                try {
                                    country = addresses.get(0).getCountryName().toString();
                                } catch (Exception e) {
                                    Log.d(TAG, "Country not found");
                                }


                                etAddress.setText(street);

                                Log.d(TAG, "Found location coordinates: " + latitude + ", " + longitude);
                                Log.d(TAG, "Found location address: " + street + ", " + city + ", " + country + ", " + zipCode);

                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                            }

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

        searchBar.setOnItemClickListener(autoCompleteClickListener);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(),
                Places.getGeoDataClient(getActivity(), null), LAT_LNG_BOUNDS, null);

        searchBar.setAdapter(placeAutocompleteAdapter);

        //=== SEARCH BUTTON KEYBOARD PRESSED ===
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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


    private void geoLocate() {
        Log.d(TAG, "geoLocate");
        String searchString = searchBar.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<android.location.Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocation Error: " + e.getMessage());
        }

        if (list.size() > 0) {
            android.location.Address address = list.get(0);

            latitude = address.getLatitude();
            longitude = address.getLongitude();


            try {
                street = address.getAddressLine(0).toString();
            } catch (Exception e) {
                street = "";
            }

            try {
                city = address.getLocality().toString();
            } catch (Exception e) {
                city = "";
            }

            try {
                country = address.getCountryName().toString();
            } catch (Exception e) {
                country = "";
            }

            try {
                zipCode = address.getPostalCode().toString();
            } catch (Exception e) {
                zipCode = "";
            }

            moveCamera(new LatLng(latitude, longitude), DEFAULT_ZOOM, "My Location");

            etAddress.setText(street);
        }
    }


    private void moveCamera(LatLng latlng, float zoom) {
        Log.d(TAG, "Move Camera: moving camera to: lat: " + latlng.latitude + ", long: " + latlng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }


    private void moveCamera(LatLng latlng, float zoom, String title) {
        Log.d(TAG, "Move Camera: moving camera to: lat: " + latlng.latitude + ", long: " + latlng.longitude);
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
