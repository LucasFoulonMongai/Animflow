package com.lfm.animflow.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class FontLoader {

    private static FontLoader instance;
    private final HashMap<String, Typeface> fontMap;

    private Context context;

    private FontLoader(Context context) {
        this.context = context;
        this.fontMap = new HashMap<>();
    }

    public static FontLoader getInstance(Context context) {
        if (instance == null) {
            instance = new FontLoader(context);
        }
        return instance;
    }


    public Typeface getFont(String fontName) {
        if (fontMap.get(fontName) != null) {
            return fontMap.get(fontName);
        } else {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            fontMap.put(fontName, typeface);
            return typeface;
        }
    }

}
