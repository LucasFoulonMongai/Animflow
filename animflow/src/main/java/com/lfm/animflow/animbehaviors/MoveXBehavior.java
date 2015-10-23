package com.lfm.animflow.animbehaviors;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public class MoveXBehavior implements AnimBehavior {

    private float fromX = 0f;
    private float finalX = -1f;

    public MoveXBehavior(float fromX) {
        this.fromX = fromX;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initFinalState(View viewToAnimate) {
        finalX = viewToAnimate.getLeft();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void animate(View viewToAnimate, float animateFactor) {
        if (fromX == 0 || finalX == -1f) {
            return;
        }
        viewToAnimate.setRight((int) (finalX + fromX - fromX * animateFactor) + viewToAnimate.getWidth());
        viewToAnimate.setLeft((int) (finalX + fromX - fromX * animateFactor));
    }

    public void setFromX(float fromX) {
        this.fromX = fromX;
    }

    public float getFromX() {
        return fromX;
    }
}
