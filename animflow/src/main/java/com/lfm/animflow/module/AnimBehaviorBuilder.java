package com.lfm.animflow.module;

import android.content.res.TypedArray;

import com.lfm.animflow.R;
import com.lfm.animflow.animbehaviors.AlphaBehavior;
import com.lfm.animflow.animbehaviors.AnimBehavior;
import com.lfm.animflow.animbehaviors.MoveXBehavior;
import com.lfm.animflow.animbehaviors.MoveYBehavior;
import com.lfm.animflow.animbehaviors.RotationBehavior;
import com.lfm.animflow.animbehaviors.ZoomBehavior;

import java.util.List;

/**
 * Created by mogwai on 23/10/15.
 */
public class AnimBehaviorBuilder {

    public static void build(List<AnimBehavior> behaviorList, TypedArray array) {
        if (array.hasValue(R.styleable.AnimatedView_fromAlpha)) {
            behaviorList.add(new AlphaBehavior(array.getFloat(R.styleable.AnimatedView_fromAlpha, -1f)));
        }
        if (array.hasValue(R.styleable.AnimatedView_fromX)) {
            behaviorList.add(new MoveXBehavior(array.getDimension(R.styleable.AnimatedView_fromX, -1f)));
        }

        if (array.hasValue(R.styleable.AnimatedView_fromY)) {
            behaviorList.add(new MoveYBehavior(array.getDimension(R.styleable.AnimatedView_fromY, -1f)));
        }

        if (array.hasValue(R.styleable.AnimatedView_fromZoom)) {
            behaviorList.add(new ZoomBehavior(array.getFloat(R.styleable.AnimatedView_fromZoom, -1f)));
        }

        if (array.hasValue(R.styleable.AnimatedView_fromRotation)) {
            behaviorList.add(new RotationBehavior(array.getFloat(R.styleable.AnimatedView_fromRotation, -1f)));
        }
    }
}
