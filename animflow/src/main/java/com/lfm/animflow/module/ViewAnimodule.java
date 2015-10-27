package com.lfm.animflow.module;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lfm.animflow.R;
import com.lfm.animflow.animbehaviors.AnimBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class ViewAnimodule {

    public final static float BOOT_ANIM_DURATION = 500.0f;

    public final static int EASE_LINEAR = 0;
    public final static int EASE_QUAD = 1;
    public final static int EASE_CUBIC = 2;
    public final static int EASE_DEFAULT = EASE_CUBIC;

    public final static int ANIMATING_STATE_IDLE = 0;
    public final static int ANIMATING_STATE_PENDING = 1;
    public final static int ANIMATING_STATE_RUNNING = 2;
    private static final AnimBehaviorBuilder DEFAULT_ANIMBEHAVIOR_BUILDER = new AnimBehaviorBuilder();

    private View viewToAnimate;

    private boolean isFromTop;
    private boolean isInited = false;
    private float animateFactor = 0f;
    private float finalAnimateFactor = 0f;

    private float delayStart = 0f;
    private float delayEnd = 1.0f;

    private int ease = EASE_CUBIC;
    private int animatingState = ANIMATING_STATE_IDLE;
    private List<AnimBehavior> animBehaviorList;

    private Runnable callbackViewRunnable = new Runnable() {
        @Override
        public void run() {
            animateInternal();
        }
    };
    private Runnable animateRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long originTime = System.currentTimeMillis();
            long currentTime = 0;
            while (animateFactor < finalAnimateFactor && currentTime < BOOT_ANIM_DURATION) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = System.currentTimeMillis() - originTime;
                animateFactor = finalAnimateFactor * ((currentTime * 1.0f) / BOOT_ANIM_DURATION);
                viewToAnimate.post(callbackViewRunnable);

            }
            animatingState = ANIMATING_STATE_IDLE;
        }
    };

    public ViewAnimodule(View viewToAnimate) {
        this(viewToAnimate, null, null);
    }

    public ViewAnimodule(View viewToAnimate, AnimBehaviorBuilder animBehaviorBuilder) {
        this(viewToAnimate, null, animBehaviorBuilder);
    }

    public ViewAnimodule(View viewToAnimate, AttributeSet attrs) {
        this(viewToAnimate, attrs, null);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ViewAnimodule(View viewToAnimate, AttributeSet attrs, AnimBehaviorBuilder animBehaviorBuilder) {
        this.viewToAnimate = viewToAnimate;
        this.animBehaviorList = new ArrayList<>();
        if (attrs != null) {
            TypedArray a = viewToAnimate.getContext().obtainStyledAttributes(attrs, R.styleable.AnimatedView, 0, 0);
            if (animBehaviorBuilder == null) {
                animBehaviorBuilder = DEFAULT_ANIMBEHAVIOR_BUILDER;
            }
            animBehaviorBuilder.build(animBehaviorList, a);

            delayStart = a.getFloat(R.styleable.AnimatedView_delayStart, 0.0f);
            delayEnd = a.getFloat(R.styleable.AnimatedView_delayEnd, 1.0f);
            isFromTop = a.getBoolean(R.styleable.AnimatedView_isFromTop, false);

            String easeStr = a.getString(R.styleable.AnimatedView_ease);
            if (easeStr == null) {
                ease = EASE_DEFAULT;
            } else if ("linear".equals(easeStr.toLowerCase())) {
                ease = EASE_LINEAR;
            } else if ("cubic".equals(easeStr.toLowerCase())) {
                ease = EASE_CUBIC;
            } else if ("quad".equals(easeStr.toLowerCase())) {
                ease = EASE_QUAD;
            } else {
                ease = EASE_DEFAULT;
            }
            a.recycle();
        } else {
            ease = EASE_DEFAULT;
        }

        if (Build.VERSION.SDK_INT >= 11) {
            viewToAnimate.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    initAnimateView();
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public synchronized void initAnimateView() {
        if (isInited || Build.VERSION.SDK_INT < 11) {
            return;
        }
        isInited = true;
        for (AnimBehavior animBehavior : animBehaviorList) {
            animBehavior.initFinalState(viewToAnimate);
        }
        animateInternal();

        if (animatingState == ANIMATING_STATE_PENDING) {
            launchAnimation();
        }
    }

    public void launchAnimation(float animateParamBottom, float animateParamTop) {
        launchAnimation(animateParamBottom, animateParamTop, false);
    }

    public void launchAnimation(float animateParamBottom, float animateParamTop, boolean skipChilds) {
        animateParamBottom = Math.max(0.0f, (animateParamBottom - delayStart) / (delayEnd - delayStart));
        animateParamTop = Math.max(0.0f, (animateParamTop - delayStart) / (delayEnd - delayStart));
        if (!skipChilds && viewToAnimate instanceof ViewGroup) {
            //float progress;
            for (int i = 0; i < ((ViewGroup) viewToAnimate).getChildCount(); i++) {
                View child = ((ViewGroup) viewToAnimate).getChildAt(i);
                if (child instanceof AnimatedView) {
                    //progress = ((child.getTop() * 1.0f) / (viewToAnimate.getHeight() * 1.0f));
                    //((AnimatedView) child).getViewAnimodule().launchAnimation((animateParam - progress) / (1.0f - progress));
                    ((AnimatedView) child).getViewAnimodule().launchAnimation(animateParamBottom, animateParamTop);
                }
            }
        }

        if (isFromTop) {
            animateParamBottom = animateParamTop;
        }
        finalAnimateFactor = smooth(Math.min(1.0f, animateParamBottom));
        if (animatingState == ANIMATING_STATE_IDLE) {
            animateFactor = 0.0f;
            launchAnimation();
        }
    }

    private synchronized void launchAnimation() {
        if (!isInited) {
            animatingState = ANIMATING_STATE_PENDING;
            return;
        }
        if (animatingState == ANIMATING_STATE_RUNNING) {
            return;
        }
        animatingState = ANIMATING_STATE_RUNNING;
        new Thread(animateRunnable).start();
    }

    public void animate(float animateParamBottom, float animateParamTop) {
        animate(animateParamBottom, animateParamTop, false);
    }

    public void animate(float animateParamBottom, float animateParamTop, boolean skipChilds) {
        animateParamBottom = Math.max(0.0f, (animateParamBottom - delayStart) / (delayEnd - delayStart));
        animateParamTop = Math.max(0.0f, (animateParamTop - delayStart) / (delayEnd - delayStart));
        if (!skipChilds && viewToAnimate instanceof ViewGroup) {
            //float progress;
            for (int i = 0; i < ((ViewGroup) viewToAnimate).getChildCount(); i++) {
                View child = ((ViewGroup) viewToAnimate).getChildAt(i);
                if (child instanceof AnimatedView) {
                    //progress = ((child.getTop() * 1.0f) / (viewToAnimate.getHeight() * 1.0f));
                    //((AnimatedView) child).getViewAnimodule().animate((animateParam - progress) / (1.0f - progress));
                    ((AnimatedView) child).getViewAnimodule().animate(animateParamBottom, animateParamTop);
                }
            }
        }

        if (isFromTop) {
            animateParamBottom = animateParamTop;
        }
        finalAnimateFactor = smooth(Math.min(1.0f, animateParamBottom));
        if (animatingState == ANIMATING_STATE_IDLE) {
            animateFactor = finalAnimateFactor;
            animateInternal();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateInternal() {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }

        if (isInited) {
            for (AnimBehavior animBehavior : animBehaviorList) {
                animBehavior.animate(viewToAnimate, animateFactor);
            }
        }
    }

    private float smooth(float min) {
        if (ease == EASE_CUBIC) {
            return --min * min * min + 1;
        }
        if (ease == EASE_QUAD) {
            return min * (2 - min);
        }
        return min;
    }

    public AnimBehavior addAnimBehavior(AnimBehavior animBehavior) {
        AnimBehavior oldBehavior = removeAnimBehavior(animBehavior.getClass());
        animBehavior.initFinalState(viewToAnimate);
        (animBehaviorList).add(animBehavior);
        return oldBehavior;
    }

    public AnimBehavior removeAnimBehavior(Class<? extends AnimBehavior> classType) {
        for (AnimBehavior animBehavior : animBehaviorList) {
            if (animBehavior.getClass().equals(classType)) {
                animBehaviorList.remove(animBehavior);
                return animBehavior;
            }
        }
        return null;
    }

    public void setEase(int ease) {
        this.ease = ease;
    }

    public float getAnimateFactor() {
        return animateFactor;
    }

    public void setAnimateFactor(float animatePar) {
        animateFactor = animatePar;
    }

    public void invalidateView() {
        isInited = false;
        initAnimateView();
    }
}
