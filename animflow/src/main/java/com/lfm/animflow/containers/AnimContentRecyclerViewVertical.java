package com.lfm.animflow.containers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.lfm.animflow.module.AnimatedView;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimContentRecyclerViewVertical extends RecyclerView {

    private boolean isInited = false;

    public AnimContentRecyclerViewVertical(Context context) {
        super(context);
        initObserver();
    }

    public AnimContentRecyclerViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObserver();
    }

    public AnimContentRecyclerViewVertical(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dy != 0) {
            animateOnScrollVertical(0, getHeight());
        }
    }

    public void animateOnScrollVertical(float scrollTop, float scrollBottom) {
        float currentTop;
        float currentBottom;
        float progressBottom;
        float progressTop;
        int currentPos;
        if (getLayoutManager() == null) {
            return;
        }
        int posLastItem = getLayoutManager().getItemCount() - 1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof AnimatedView) {
                currentPos = getChildAdapterPosition(child);
                currentTop = child.getTop();
                currentBottom = child.getBottom();
                progressBottom = (scrollBottom - currentTop) / (currentBottom - currentTop);
                progressTop = (scrollTop - currentTop) / (currentBottom - currentTop);

                //Vertical move of the main child currently not supported, so better to remove them
                ((AnimatedView) child).getViewAnimodule().setFromY(0f);

                if (scrollTop == 0.001234f) {
                    ((AnimatedView) child).getViewAnimodule().launchAnimation(progressBottom, progressTop);
                } else {
                    ((AnimatedView) child).getViewAnimodule().animate(progressBottom * (currentPos == posLastItem ? 2.0f : 1.0f), progressTop* (currentPos == posLastItem ? 2.0f : 1.0f));// put between 1.0 et 3.0 to speed the last item
                }
            }
        }
    }

    public void initAnimatedView() {
        if (!isInited) {
            isInited = true;
            animateOnScrollVertical(0.001234f, getHeight());
        }
    }

}
