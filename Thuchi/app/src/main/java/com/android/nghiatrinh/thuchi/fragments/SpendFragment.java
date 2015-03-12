package com.android.nghiatrinh.thuchi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nghiatrinh.thuchi.R;

/**
 * Created by NghiaTrinh on 3/2/2015.
 */
public class SpendFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.spend_fragment_layout,container,false);
        return view;
    }
}
