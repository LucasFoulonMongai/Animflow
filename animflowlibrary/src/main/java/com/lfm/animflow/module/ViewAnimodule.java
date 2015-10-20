package com.lfm.animflow.module;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lfm.animflow.R;

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


    private Context context;
    private View viewToAnimate;

    private boolean isFromTop;
    private boolean isAnimated = false;
    private boolean isInited = false;
    private float animateFactor = 0f;
    private float finalAnimateFactor = 0f;
    private float finalX = 0f;
    private float fromX = 0f;

    private float finalY = 0f;
    private float fromY = 0f;

    private float finalAlpha = 1.0f;
    private float fromAlpha = 0f;

    private float finalZoom = 1.0f;
    private float fromZoom = 0f;

    private float delayStart = 0f;
    private float delayEnd = 1.0f;

    private float finalRotation = 0f;
    private float fromRotation = 0f;

    private int ease = EASE_CUBIC;
    private int animatingState = ANIMATING_STATE_IDLE;

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

    public ViewAnimodule(View viewToAnimate, Context context) {
        this(viewToAnimate, context, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ViewAnimodule(View viewToAnimate, Context context, AttributeSet attrs) {
        this.viewToAnimate = viewToAnimate;
        this.context = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedView, 0, 0);
            fromAlpha = a.getFloat(R.styleable.AnimatedView_fromAlpha, finalAlpha);
            fromX = a.getDimension(R.styleable.AnimatedView_fromX, 0.0f);
            fromY = a.getDimension(R.styleable.AnimatedView_fromY, 0.0f);
            fromZoom = a.getFloat(R.styleable.AnimatedView_fromZoom, finalZoom) - 1.0f;
            fromRotation = a.getFloat(R.styleable.AnimatedView_fromRotation, finalRotation);
            delayStart = a.getFloat(R.styleable.AnimatedView_delayStart, 0.0f);
            delayEnd = a.getFloat(R.styleable.AnimatedView_delayEnd, 1.0f);
            isFromTop = a.getBoolean(R.styleable.AnimatedView_isFromTop, false);
            checkIsAnimated();
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
            isAnimated = false;
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
        finalX = viewToAnimate.getLeft();
        finalY = viewToAnimate.getTop();
        finalAlpha = viewToAnimate.getAlpha();
        finalZoom = viewToAnimate.getScaleX();
        finalRotation = viewToAnimate.getRotation();
        checkIsAnimated();
        if (!isAnimated) {
            return;
        }
        if (finalAlpha != fromAlpha) {
            viewToAnimate.setAlpha((finalAlpha - fromAlpha) * animateFactor + fromAlpha);
        }

        if (0f != fromX) {
            viewToAnimate.setRight((int) (finalX + fromX - fromX * animateFactor) + viewToAnimate.getWidth());
            viewToAnimate.setLeft((int) (finalX + fromX - fromX * animateFactor));
        }

        if (0f != fromY) {
            viewToAnimate.setTop((int) (finalY + fromY - fromY * animateFactor));
        }

        if ((finalZoom - 1f) != fromZoom) {
            viewToAnimate.setScaleX(finalZoom + fromZoom - fromZoom * animateFactor);
            viewToAnimate.setScaleY(finalZoom + fromZoom - fromZoom * animateFactor);
        }

        if (finalRotation != fromRotation) {
            viewToAnimate.setRotation(finalRotation + fromRotation - fromRotation * animateFactor);
        }

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
        if (!isAnimated) {
            animatingState = ANIMATING_STATE_IDLE;
            return;
        }
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
        if (!isAnimated || Build.VERSION.SDK_INT < 11) {
            return;
        }

        if (isInited) {
            if (finalAlpha != fromAlpha) {
                viewToAnimate.setAlpha((finalAlpha - fromAlpha) * animateFactor + fromAlpha);
            }

            if (fromX != 0f) {
                viewToAnimate.setRight((int) (finalX + fromX - fromX * animateFactor) + viewToAnimate.getWidth());
                viewToAnimate.setLeft((int) (finalX + fromX - fromX * animateFactor));
            }

            if (fromY != 0f) {
                viewToAnimate.setTop((int) (finalY + fromY - fromY * animateFactor));
            }

            if ((finalZoom - 1f) != fromZoom) {
                viewToAnimate.setScaleX(finalZoom + fromZoom - fromZoom * animateFactor);
                viewToAnimate.setScaleY(finalZoom + fromZoom - fromZoom * animateFactor);
            }

            if (finalRotation != fromRotation) {
                viewToAnimate.setRotation(finalRotation + fromRotation - fromRotation * animateFactor);
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

    private void checkIsAnimated() {
        isAnimated = delayStart < delayEnd
                && (fromAlpha != finalAlpha
                || fromX != 0f
                || fromY != 0f
                || fromZoom != (finalZoom - 1f)
                || fromRotation != finalRotation);
    }

    public void setFromAlpha(float fromAlpha) {
        this.fromAlpha = fromAlpha;
        checkIsAnimated();
    }

    public void setDelayStart(float delayStart) {
        this.delayStart = delayStart;
        checkIsAnimated();
    }

    public void setDelayEnd(float delayEnd) {
        this.delayEnd = delayEnd;
        checkIsAnimated();
    }

    public void setFromZoom(float fromZoom) {
        this.fromZoom = fromZoom - 1f;
        checkIsAnimated();
    }

    public void setFromX(float fromX) {
        this.fromX = fromX;
        checkIsAnimated();
    }

    public void setFromY(float fromY) {
        this.fromY = fromY;
        checkIsAnimated();
    }

    public void setFinalAlpha(float finalAlpha) {
        this.finalAlpha = finalAlpha;
        checkIsAnimated();
    }

    public void setFinalRotation(float finalRotation) {
        this.finalRotation = finalRotation;
        checkIsAnimated();
    }

    public void setFinalX(float finalX) {
        this.finalX = finalX;
        checkIsAnimated();
    }

    public void setFinalY(float finalY) {
        this.finalY = finalY;
        checkIsAnimated();
    }

    public void setFinalZoom(float finalZoom) {
        this.finalZoom = finalZoom;
        checkIsAnimated();
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
