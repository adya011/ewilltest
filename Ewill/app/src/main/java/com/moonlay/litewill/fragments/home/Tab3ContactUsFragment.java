package com.moonlay.litewill.fragments.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonlay.litewill.R;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3ContactUsFragment extends Fragment {


    public Tab3ContactUsFragment() {
        // Required empty public constructor
    }

    public static Tab3ContactUsFragment newInstance(){
        Tab3ContactUsFragment fragment = new Tab3ContactUsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_tab4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ZopimChat.init("5l2MyuEz8ZttveJcUkQDEAiSwJEbkfT1");
        initView();
    }

    private void initView() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_tab4_layout, new ZopimChatFragment(), ZopimChatFragment.class.getName());
        ft.commit();
    }
}
