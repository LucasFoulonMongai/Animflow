package com.lfm.animflowsample.presenters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfm.animflowsample.R;
import com.lfm.animflowsample.models.DataItem;
import com.lfm.rvgenadapter.ViewPresenter;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class ItemType1Presenter extends ViewPresenter<DataItem> {
    private View view;

    private TextView itemTitle;
    private TextView itemDescription;

    public ItemType1Presenter() {
        super();
    }


    @Override
    public void initViewPresenter(Context context, ViewGroup parent, Bundle params) {
        this.view = LayoutInflater.from(context).inflate(R.layout.item_type_1, parent, false);

        mapViews(getView());

        if (getOnClickListener() != null) {
            view.setOnClickListener(getOnClickListener());
        }
    }

    private void mapViews(View view) {
        itemTitle = (TextView) view.findViewById(R.id.itemTitle);
        itemDescription = (TextView) view.findViewById(R.id.itemDescription);
    }

    @Override
    public void refresh() {
        if (view == null) {
            return;
        }

        DataItem data = getData();
        if (data != null) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
            return;
        }

        itemTitle.setText(data.getTitre());
        itemDescription.setText(data.getDescription());

    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
