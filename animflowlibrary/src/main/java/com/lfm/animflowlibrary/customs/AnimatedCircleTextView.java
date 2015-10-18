package com.lfm.animflowlibrary.customs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lfm.animflowlibrary.classics.AnimatedTextView;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class AnimatedCircleTextView extends AnimatedTextView {
    private float centerX = -1;
    private float centerY = -1;
    private float radius = -1;
    private boolean clockwise = true;
    private String headline = "";
    private int color;
    private Paint paintHead;
    private Paint paintSep;
    private Paint myPaint;
    private String sep = "";
    private Rect rectHead;
    private Rect rectSep;
    private Rect rectText;
    private Path path;

    public AnimatedCircleTextView(Context context) {
        super(context);
    }

    public AnimatedCircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedCircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initRotation(float centerX, float centerY, float radius, boolean clockwise) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.clockwise = clockwise;
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if (centerX == -1 || centerY == -1 || radius == -1 || myPaint == null || paintHead == null || paintSep == null) {
            //super.onDraw(canvas);
            return;
        }

        String text = String.valueOf(getText());

        //on place les mesures du texte dans un rectange
        myPaint.getTextBounds(text, 0, text.length(), rectText);
        paintSep.getTextBounds(sep.replaceAll(" ","("), 0, sep.length(), rectSep);
        paintHead.getTextBounds(headline, 0, headline.length(), rectHead);

        //on crée un rectangle pour tracer le cercle (un oval particulier) d'ecriture à l'intérieur
        RectF cercle = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // calcul de l'angle a placer de chaque coté du texte pour le centrer sur le cercle
        float angle = Math.max(0f, 360.0f * (1.0f - (rectText.width() + rectSep.width() + rectHead.width()) / ((float) (2.0f * radius * Math.PI)))) / 2.0f;

        //on trace l'arc de cercle (sur lequel on ecrit) dans le bon sens, en haut lorsque c'est clockwise, en bas lorsque c'est counter
        path.addArc(cercle, (clockwise ? 90 + angle : -90 - angle), (clockwise ? (360.0f - angle) : -(360.0f - angle)));

        if (color != -1 && headline != null) {
            canvas.drawTextOnPath(sep, path, rectText.width(), 0, paintSep);
            canvas.drawTextOnPath(headline, path, rectText.width() + rectSep.width()*0.9f , 0, paintHead);
        }

        //on ecrit sur le cercle
        canvas.drawTextOnPath(text, path, 0, 0, myPaint);
    }

    public void setText(String texte, String headline) {
        this.headline = headline;
        this.sep = " | ";
        super.setText(texte);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        color = getContext().getResources().getColor(android.R.color.black);

        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setColor(getTextColors().getDefaultColor());
        myPaint.setTextSize(getTextSize());
        myPaint.setTypeface(getTypeface());

        paintHead = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHead.setColor(getTextColors().getDefaultColor());
        paintHead.setTextSize(getTextSize());
        paintHead.setTypeface(Typeface.create(getTypeface(), Typeface.BOLD));

        paintSep = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSep.setColor(color);
        paintSep.setTextSize(getTextSize() * 1.2f);
        paintSep.setTypeface(getTypeface());

        path = new Path();
        rectText = new Rect();
        rectSep = new Rect();
        rectHead = new Rect();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myPaint.setLetterSpacing(getLetterSpacing());
            paintHead.setLetterSpacing(getLetterSpacing());
            paintSep.setLetterSpacing(getLetterSpacing());
        }

        super.setText(text, type);
    }
}
