package com.lfm.animflow.classics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lfm.animflow.module.AnimatedView;
import com.lfm.animflow.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedImageView extends ImageView implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedImageView(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this);
    }

    public AnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, attrs);
    }

    public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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