package com.lfm.animflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfm.animflow.R;
import com.lfm.animflowlibrary.containers.AnimContentScrollView;
import com.lfm.animflowlibrary.classics.AnimatedLinearLayout;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class FlowContentFragment extends Fragment{
    private View view;

    private AnimContentScrollView contentScrollView;
    private AnimatedLinearLayout contentContainerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentScrollView = (AnimContentScrollView) view.findViewById(R.id.contentScrollView);
        contentContainerLayout  = (AnimatedLinearLayout) view.findViewById(R.id.contentContainerLayout);
    }
}