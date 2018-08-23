package com.moonlay.litewill.fragments.reg_will;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.utility.MyUtility;
import com.moonlay.litewill.utility.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 *
 * Activity: WillActivity
 */
public class Regw1WillNameFragment extends BaseFragment {
    Button btnNext;
    TextInputEditText etWillName;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    final Document documents[] = new Document[2];
    final Shared[] shareds = new Shared[1];
    final Address[] addresses = new Address[1];

    private static final String TAG = "mydebug_regwill1";

    private static final String HEADER_AGENT = "mobile";
    private static final String HEADER_VERSION = "1.0";
    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImQxNGZjYjNkYzEzZTk3OTRmYjhkZmU2YjIwMTBjMTIzIiwidHlwIjoiSldUIn0.eyJuYmYiOjE1MzA1MDY2NDUsImV4cCI6MTUzMDUxMDI0NSwiaXNzIjoibnVsbCIsImF1ZCI6WyJudWxsL3Jlc291cmNlcyIsImV3aWxsIl0sImNsaWVudF9pZCI6Im1vYmlsZS5kcm9pZCIsInN1YiI6IjkyYmUyYjZmLTFhY2EtNDUxYi1iODEyLTZhNjIzNDI3YTAwYiIsImF1dGhfdGltZSI6MTUzMDUwNjY0NSwiaWRwIjoibG9jYWwiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiam9obmRvZTAzIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoiZXdpbGx0c3RAZ21haWwuY29tIiwic2NvcGUiOlsicHJvZmlsZSIsImV3aWxsIiwib2ZmbGluZV9hY2Nlc3MiXSwiYW1yIjpbInB3ZCJdfQ.D_ZvP5CDNLE1q3Vlx-d4GDo_-CtDiJVl2HvE-YCj0QnQ75gweLMXIK-YeOsRtL3k8_PV8gewsuxRFOEPRmhMd7ww3LRFMOlg3u6T2vlT5t8SFdc5dqEVFS45dtpkdCu2G1kjeCNs_-MZM3fh5opK4x3gvojH649OgWRkAAmuQlun3Ly6g4WN3bDQ4buM1RbV4VZ6GZqOiWzAinXdTJeB02vHcsYyjpQJmIlk4uTvmv38Kyf4hFhJZagYy6RwSc08wufbh9iM7wGKDa7iH_N2-bCAYviWkrtE_mIsJQOkly1EjqlQM-HkM0jYqKd9PCSpoBAuSpIr3md5I5gjYZ08uA";

    public Regw1WillNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg17_will_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);
        btnNext = mView.findViewById(R.id.btn_next);
        etWillName = mView.findViewById(R.id.et_willname);

        initView();
    }

    private void initView() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        MyUtility.askForPermission(getActivity(), getContext(), permissions);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((RegWillActivity)getActivity()).willName = etWillName.getText().toString();
                String willName = etWillName.getText().toString();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_will, Regw2MapFragment.newInstance(willName));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
