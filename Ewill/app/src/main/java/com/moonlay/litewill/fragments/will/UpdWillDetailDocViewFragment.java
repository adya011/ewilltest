package com.moonlay.litewill.fragments.will;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.WillActivity;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdWillDetailDocViewFragment extends BaseFragment {
    private static final String TAG = "mydebug_photoview";
    WebView webView;
    String imgName;

    public UpdWillDetailDocViewFragment() {
        // Required empty public constructor
    }

    public static UpdWillDetailDocViewFragment newInstace(String imgName) {
        UpdWillDetailDocViewFragment frag = new UpdWillDetailDocViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("img_name", imgName);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upd_will_detail_doc_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.webView);

        //((WillActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActivity().setTitle("Will Document");
        init();
    }

    private void init() {
        imgName = getArguments().getString("img_name");
        String imgUrl = Constants.STORAGE_URL + imgName;

        //((WillActivity) getActivity()).setActionBarTitle(imgName);
        getActivity().setTitle(imgName);

        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(imgUrl);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);

        Log.d(TAG, "Image URL: " + imgUrl);
    }
}
