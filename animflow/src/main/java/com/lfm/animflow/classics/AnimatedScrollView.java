package com.lfm.animflow.classics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedScrollView extends ScrollView implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedScrollView(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this);
    }

    public AnimatedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, attrs);
    }

    public AnimatedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }
}