package com.lfm.animflowsample.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfm.animflowsample.R;
import com.lfm.animflow.classics.AnimatedLinearLayout;
import com.lfm.animflow.containers.AnimContentScrollView;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class FlowContent2Fragment extends Fragment{
    private View view;

    private AnimContentScrollView contentScrollView;
    private AnimatedLinearLayout contentContainerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content_bis, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentScrollView = (AnimContentScrollView) view.findViewById(R.id.contentScrollView);
        contentContainerLayout  = (AnimatedLinearLayout) view.findViewById(R.id.contentContainerLayout);
    }
}