package com.lfm.animflow.classics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedRelativeLayout extends RelativeLayout implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedRelativeLayout(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this, context);
    }

    public AnimatedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    public AnimatedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }
}