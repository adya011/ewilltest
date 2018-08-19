package com.moonlay.litewill.fragments.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonlay.litewill.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2MyAccountFragment extends Fragment {
    public Tab2MyAccountFragment() {
        // Required empty public constructor
    }

    public static Tab2MyAccountFragment newInstance() {
        Tab2MyAccountFragment fragment = new Tab2MyAccountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_tab2_layout, new MyAccountFragment()).commit();
    }
}
