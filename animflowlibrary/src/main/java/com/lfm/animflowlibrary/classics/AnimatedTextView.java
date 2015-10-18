package com.lfm.animflowlibrary.classics;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfm.animflowlibrary.customs.AnimatedFontTextView;
import com.lfm.animflowlibrary.module.AnimatedView;
import com.lfm.animflowlibrary.module.ViewAnimodule;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedTextView extends TextView implements AnimatedView {

    private ViewAnimodule viewAnimodule;

    public AnimatedTextView(Context context) {
        super(context);
        viewAnimodule = new ViewAnimodule(this, context);
    }

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    public AnimatedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAnimodule = new ViewAnimodule(this, context, attrs);
    }

    @Override
    public ViewAnimodule getViewAnimodule() {
        return viewAnimodule;
    }
}