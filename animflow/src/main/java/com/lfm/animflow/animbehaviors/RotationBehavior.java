package com.lfm.animflow.animbehaviors;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public class RotationBehavior implements AnimBehavior {

    private float fromRotation = 0f;
    private float finalRotation = -1f;

    public RotationBehavior(float fromRotation) {
        this.fromRotation = fromRotation;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initFinalState(View viewToAnimate) {
        finalRotation = viewToAnimate.getRotation();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void animate(View viewToAnimate, float animateFactor) {
        if (finalRotation != fromRotation || finalRotation == -1f) {
            return;
        }
        viewToAnimate.setRotation(finalRotation + fromRotation - fromRotation * animateFactor);
    }

    public void setFromRotation(float fromRotation) {
        this.fromRotation = fromRotation;
    }

    public float getFromRotation() {
        return fromRotation;
    }
}
