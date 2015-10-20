package com.lfm.animflow.classics;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedCardView extends CardView implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedCardView(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this, context);
    }

    public AnimatedCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    public AnimatedCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }
}
