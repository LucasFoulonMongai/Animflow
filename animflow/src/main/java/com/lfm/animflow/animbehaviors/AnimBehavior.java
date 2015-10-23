package com.lfm.animflow.animbehaviors;

import android.content.res.TypedArray;
import android.view.View;

/**
 * Created by mogwai on 23/10/15.
 */
public interface AnimBehavior {
    void initFinalState(View viewToAnimate);
    void animate(View viewToAnimate, float animateFactor);
}
