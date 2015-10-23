package com.lfm.animflow.animbehaviors;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public class MoveYBehavior implements AnimBehavior {

    private float fromY = 0f;
    private float finalY = -1f;

    public MoveYBehavior(float fromY) {
        this.fromY = fromY;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initFinalState(View viewToAnimate) {
        finalY = viewToAnimate.getLeft();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void animate(View viewToAnimate, float animateFactor) {
        if (fromY == 0 || finalY == -1f) {
            return;
        }
        viewToAnimate.setTop((int) (finalY + fromY - fromY * animateFactor));
    }

    public void setFromY(float fromY) {
        this.fromY = fromY;
    }

    public float getFromY() {
        return fromY;
    }
}
