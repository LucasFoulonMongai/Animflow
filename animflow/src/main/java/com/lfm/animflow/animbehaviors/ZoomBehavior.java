package com.lfm.animflow.animbehaviors;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public class ZoomBehavior implements AnimBehavior {

    private float fromZoomX = 0f;
    private float finalZoomX = -1f;
    private float fromZoomY = 0f;
    private float finalZoomY = -1f;

    public ZoomBehavior(float fromZoom) {
        this.fromZoomX = fromZoom;
        this.fromZoomY = fromZoom;
    }

    public ZoomBehavior(float fromZoomX, float fromZoomY) {
        this.fromZoomX = fromZoomX;
        this.fromZoomY = fromZoomY;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initFinalState(View viewToAnimate) {
        finalZoomX = viewToAnimate.getScaleX();
        finalZoomY = viewToAnimate.getScaleY();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void animate(View viewToAnimate, float animateFactor) {
        if ((finalZoomX - 1f) != fromZoomX || finalZoomX == -1f ||  (finalZoomX - 1f) != fromZoomX || finalZoomY == -1f) {
            return;
        }
        viewToAnimate.setScaleX(finalZoomX + fromZoomX - fromZoomX * animateFactor);
        viewToAnimate.setScaleY(finalZoomY + fromZoomY - fromZoomY * animateFactor);
    }

    public void setFromZoom(float fromZoomX, float fromZoomY) {
        this.fromZoomX = fromZoomX;
        this.fromZoomY = fromZoomY;
    }

    public float getFromZoomX() {
        return fromZoomX;
    }

    public float getFromZoomY() {
        return fromZoomY;
    }
}
