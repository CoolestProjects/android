package com.mttnow.coolestprojects.screens.fragments.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mttnow.coolestprojects.R;


public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] font = new int[]{R.attr.font};
        int indexOfAttrFont = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, font);
        String fontName = a.getString(indexOfAttrFont);
        a.recycle();
        setFont(fontName);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int[] font = new int[]{R.attr.font};
        int indexOfAttrFont = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, font);
        String fontName = a.getString(indexOfAttrFont);
        a.recycle();
        setFont(fontName);
    }

    public CustomTextView(Context context, String typeface) {
        super(context);
        setTypeface(typeface);
    }

    private void setFont(String ttfName) {
        if (ttfName == null)
            throw new RuntimeException("Missing font value in View with id "
                    + getId());
        setTypeface(ttfName);
    }

    public void setTypeface(String typeface) {

        setTypeface(Typefaces.get(this.getContext(), typeface));
    }
}
