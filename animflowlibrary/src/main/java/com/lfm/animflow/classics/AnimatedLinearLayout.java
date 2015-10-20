package com.lfm.animflow.classics;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedLinearLayout extends LinearLayout implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedLinearLayout(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this, context);
    }

    public AnimatedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AnimatedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }
}