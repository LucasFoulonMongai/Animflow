package com.lfm.animflowsample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfm.animflowsample.R;
import com.lfm.animflowsample.models.DataItem;
import com.lfm.animflowsample.presenters.ItemType1Presenter;
import com.lfm.rvgenadapter.GenericRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class FlowItemsFragment extends Fragment {
    private View view;

    private Context context;

    private RecyclerView itemsRecyclerView;
    private List<DataItem> datasList;
    private GenericRecyclerAdapter<DataItem> datasAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        itemsRecyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);

        loadData();
    }

    private void loadData() {
        datasList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            datasList.add(new DataItem("Item " + i, "Description " + i));
        }
        showData();
    }

    private void showData() {
        if (datasList == null) {
            return;
        }

        if (datasAdapter == null) {
            datasAdapter = new GenericRecyclerAdapter<>(context, datasList, ItemType1Presenter.class);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            itemsRecyclerView.setAdapter(datasAdapter);
        } else {
            datasAdapter.setItems(datasList);
        }
    }
}