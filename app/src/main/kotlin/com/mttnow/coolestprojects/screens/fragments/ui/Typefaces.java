
package com.mttnow.coolestprojects.screens.fragments.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Typefaces {

    private static final Map<String, Typeface> cache = new HashMap<>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), "fonts/" + assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e("getTypeface failed", e.getMessage());
                }
            }
            return cache.get(assetPath);
        }
    }

}
