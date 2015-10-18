package com.lfm.animflowlibrary.containers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.lfm.animflowlibrary.classics.AnimatedLinearLayout;
import com.lfm.animflowlibrary.module.AnimatedView;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimContentScrollView extends ScrollView {


    private boolean isInited = false;
    private AnimatedLinearLayout containerView;


    public AnimContentScrollView(Context context) {
        super(context);
        initObserver();
    }

    public AnimContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObserver();
    }

    public AnimContentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObserver();
    }

    private void initObserver() {
        isInited = false;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initAnimatedView();
            }
        });
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof AnimatedLinearLayout) {
            containerView = (AnimatedLinearLayout) child;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (containerView != null && t != oldt) {
            animateOnScroll(t, t + getHeight());
        }
    }

    public void initAnimatedView() {
        if (containerView != null && !isInited) {
            isInited = true;
            animateOnScroll(0.001234f, getHeight());
        }
    }

    public void animateOnScroll(float scrollTop, float scrollBottom) {
        float currentTop;
        float currentBottom;
        float progressBottom;
        float progressTop;
        int currentPos = 0;
        int posLastItem = getChildCount() - 1;
        for (int i = 0; i < containerView.getChildCount(); i++) {
            View child = containerView.getChildAt(i);
            if (child instanceof AnimatedView) {
                currentTop = child.getTop();
                currentBottom = child.getBottom();
                if (scrollTop > currentBottom) {
                    //Previous view not shown, do nothing
                } else if (scrollBottom > currentTop) {
                    progressBottom = (scrollBottom - currentTop) / (currentBottom - currentTop);
                    progressTop = (scrollTop - currentTop) / (currentBottom - currentTop);
                    if (scrollTop == 0.001234f) {
                        ((AnimatedView) child).getViewAnimodule().launchAnimation(progressBottom, progressTop);
                    } else {
                        ((AnimatedView) child).getViewAnimodule().animate(progressBottom * (currentPos == posLastItem ? 2.0f : 1.0f), progressTop * (currentPos == posLastItem ? 2.0f : 1.0f));// put between 1.0 et 3.0 to speed the last item
                    }
                } else {
                    //Following view not shown, skip them
                    break;
                }
                currentPos++;
            }
        }
    }

}
