package com.lfm.animflow.animbehaviors;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public class AlphaBehavior implements AnimBehavior {
    private float fromAlpha = 0f;
    private float finalAlpha = -1f;

    public AlphaBehavior(float fromAlpha) {
        this.fromAlpha = fromAlpha;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initFinalState(View viewToAnimate) {
        finalAlpha = viewToAnimate.getAlpha();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void animate(View viewToAnimate, float animateFactor) {
        if (fromAlpha == finalAlpha || finalAlpha == -1f ) {
            return;
        }
        viewToAnimate.setAlpha((finalAlpha - fromAlpha) * animateFactor + fromAlpha);
    }

    public void setFromAlpha(float fromAlpha) {
        this.fromAlpha = fromAlpha;
    }

    public float getFromAlpha() {
        return fromAlpha;
    }
}
