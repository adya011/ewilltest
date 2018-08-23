package com.moonlay.litewill.fragments.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonlay.litewill.MainActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.change.ChangeEmail3Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1HomeFragment extends BaseFragment {
    String uriToken = null;
    String username = null;
    String newEmail = null;

    public Tab1HomeFragment() {
        // Required empty public constructor
    }

    public static Tab1HomeFragment newInstance() {
        Tab1HomeFragment fragment = new Tab1HomeFragment();
        return fragment;
    }

    public static Tab1HomeFragment newInstance(String uriToken, String username, String newEmail) {
        Tab1HomeFragment fragment = new Tab1HomeFragment();
        Bundle args = new Bundle();
        args.putString("uriToken", uriToken);
        args.putString("username", username);
        args.putString("newEmail", newEmail);
        Log.d("mydebug_tab1", "tab1 start");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (MainActivity.isDeepLinkChangeEmail) {
            Log.d("mydebug_tab1", "deep link true");
            FragmentTransaction ft2 = getFragmentManager().beginTransaction();
            ft2.replace(R.id.frame_tab1_layout, new ChangeEmail3Fragment());
            ft2.commit();

        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_tab1_layout, new HomeFragment());
            ft.commit();
        }
    }
}
