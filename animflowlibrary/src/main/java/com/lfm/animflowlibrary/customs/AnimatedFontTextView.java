package com.lfm.animflowlibrary.customs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;

import com.lfm.animflowlibrary.R;
import com.lfm.animflowlibrary.classics.AnimatedTextView;
import com.lfm.animflowlibrary.utils.FontLoader;

public class AnimatedFontTextView extends AnimatedTextView {

    private static boolean testLetterSpacing = false;
    private float letterSpacing;
    private int maxLines;
    private CharSequence originalText = "";
    private String fontName;
    private String fontNameSelected;

    private boolean hasKerningFonction = false;

    public AnimatedFontTextView(Context context) {
        super(context);
    }

    public AnimatedFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initTextView(context, attrs);
    }

    public AnimatedFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTextView(context, attrs);
    }

    public AnimatedFontTextView(Context context, String fontName) {
        super(context);
        if (isInEditMode()) {
            return;
        }
        this.fontName = fontName;
        init(fontName);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTextView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedFontTextView, 0, 0);
        fontName = a.getString(R.styleable.AnimatedFontTextView_ttfNameTextView);
        fontNameSelected = a.getString(R.styleable.AnimatedFontTextView_ttfNameTextViewSelected);
        letterSpacing = a.getFloat(R.styleable.AnimatedFontTextView_letterSpacing, LetterSpacing.NORMAL);
        final float shadowRadius = a.getDimension(R.styleable.AnimatedFontTextView_shadowRadius, 0f);
        final float shadowDx = a.getDimension(R.styleable.AnimatedFontTextView_shadowDx, 0f);
        final float shadowDy = a.getDimension(R.styleable.AnimatedFontTextView_shadowDy, 0f);
        final int shadowColor = a.getColor(R.styleable.AnimatedFontTextView_shadowColor, 0);
        if (shadowColor != 0) {
            setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        } else {
            getPaint().clearShadowLayer();
        }
        a.recycle();

        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.maxLines});
        maxLines = array.getInt(0, -1);
        array.recycle();

        init(fontName);

        try {
            letterSpacing = getLetterSpacing();
            hasKerningFonction = true;
        } catch (Exception e) {
            hasKerningFonction = true;
        } catch (Error e) {
            hasKerningFonction = true;
        }
        if ((testLetterSpacing || !hasKerningFonction && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) && letterSpacing != LetterSpacing.NORMAL) {
            setText(getText());
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (fontNameSelected != null) {
            if (selected) {
                init(fontNameSelected);
            } else {
                init(fontName);
            }
        }
        super.setSelected(selected);
    }

    @Override
    public int getMaxLines() {
        return maxLines;
    }

    @Override
    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    private void init(String fontName) {
        setTypeface(FontLoader.getInstance(getContext()).getFont(fontName));
    }

    public void refreshFont(String fontName) {
        setTypeface(FontLoader.getInstance(getContext()).getFont(fontName));
    }

    private void applyLetterSpacing() {
        if (letterSpacing == LetterSpacing.NORMAL) {
            super.setText(originalText, BufferType.NORMAL);
        } else {
            super.setText(applyKerning(originalText, letterSpacing), BufferType.SPANNABLE);
        }
    }

    public static Spannable applyKerning(CharSequence src, float kerning) {
        if (src == null) return null;
        final int srcLength = src.length();
        if (srcLength < 2) return src instanceof Spannable
                ? (Spannable) src
                : new SpannableString(src);

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                ? (SpannableStringBuilder) src
                : new SpannableStringBuilder(src);
        for (int i = src.length() - 1; i >= 1; i--) {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }

    public void setLetterSpacing(float letterSpacing) {
        if (!hasKerningFonction && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            originalText = (originalText == null ? "" : originalText);
            this.letterSpacing = letterSpacing;
            applyLetterSpacing();
        } else {
            super.setLetterSpacing(letterSpacing);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (testLetterSpacing || !hasKerningFonction && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            originalText = (text == null ? "" : text);
            applyLetterSpacing();
        } else {
            super.setText(text, type);
        }
    }

    public class LetterSpacing {
        public final static float NORMAL = 0f;
    }
}