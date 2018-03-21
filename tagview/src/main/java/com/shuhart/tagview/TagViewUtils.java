package com.shuhart.tagview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TagViewUtils {

    static int pullAttr(final Context context, @Nullable AttributeSet attrs, int[] attrId) {
        if (attrs == null) return ViewTagStubs.EMPTY_RESOURCE;
        final TypedArray ta = context.obtainStyledAttributes(attrs, attrId);
        if (ta == null) return ViewTagStubs.EMPTY_RESOURCE;
        try {
            return ta.getResourceId(0, ViewTagStubs.EMPTY_RESOURCE);
        } catch (Exception ignored) {
            // Failed for some reason
            return ViewTagStubs.EMPTY_RESOURCE;
        } finally {
            ta.recycle();
        }
    }

    public static int getTag(View view, @IdRes int key) {
        Object tag = view.getTag(key);
        if (tag != null && tag instanceof Integer) {
            return ((int) tag);
        }
        return ViewTagStubs.EMPTY_RESOURCE;
    }

    public static void setTag(View view, @IdRes int key, String tag) {
        view.setTag(key, tag);
    }

    public static void setTag(View view, @IdRes int key, @AnyRes int id) {
        view.setTag(key, id);
    }

    public static void setBackground(View view, @DrawableRes int id) {
        view.setBackgroundResource(id);
        setTag(view, ViewTag.VIEW_BACKGROUND.id, id);
    }

    public static void clearBackground(View view) {
        view.setBackground(null);
        view.setTag(ViewTag.VIEW_BACKGROUND.id, ViewTagStubs.EMPTY_RESOURCE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setForeground(View view, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || view instanceof FrameLayout) {
            Context context = view.getContext();
            view.setForeground(getDrawable(context, id));
            setTag(view, ViewTag.VIEW_FOREGROUND.id, id);
        }
    }

    @Nullable
    private static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (id == 0) return null;
        return ContextCompat.getDrawable(context, id);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void clearForeground(View view) {
        view.setForeground(null);
        view.setTag(ViewTag.VIEW_FOREGROUND.id, ViewTagStubs.EMPTY_RESOURCE);
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setTextViewCompoundDrawablesRelativeWithIntrinsicBounds(TextView view,
                                                                               @DrawableRes int left,
                                                                               @DrawableRes int top,
                                                                               @DrawableRes int right,
                                                                               @DrawableRes int bottom) {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, left);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_TOP.id, top);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, right);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_BOTTOM.id, bottom);
    }

    public static void setTextViewCompoundDrawables(TextView view,
                                                    @DrawableRes int left,
                                                    @DrawableRes int top,
                                                    @DrawableRes int right,
                                                    @DrawableRes int bottom) {
        Context context = view.getContext();
        view.setCompoundDrawables(getDrawable(context, left),
                getDrawable(context, top),
                getDrawable(context, right),
                getDrawable(context, bottom));
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, left);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_TOP.id, top);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, right);
        setTag(view, ViewTag.TEXTVIEW_DRAWABLE_BOTTOM.id, bottom);
    }

    public static void setImageViewResource(ImageView view, @DrawableRes int id) {
        view.setBackgroundResource(id);
        setTag(view, ViewTag.IMAGEVIEW_SRC.id, id);
    }

    public static void clearImageViewResource(ImageView view) {
        view.setImageDrawable(null);
        view.setTag(ViewTag.IMAGEVIEW_SRC.id, ViewTagStubs.EMPTY_RESOURCE);
    }
}
