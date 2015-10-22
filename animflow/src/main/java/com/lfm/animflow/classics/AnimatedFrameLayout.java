package com.lfm.animflow.classics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedFrameLayout extends FrameLayout implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedFrameLayout(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this);
    }

    public AnimatedFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, attrs);
    }

    public AnimatedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }

    @Override
    public void customAnimation(float animationFactor) {

    }
}