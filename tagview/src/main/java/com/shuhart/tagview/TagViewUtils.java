package com.shuhart.tagview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
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

    public static void setBackground(View view, @DrawableRes int id) {
        view.setBackgroundResource(id);
        setBackgroundTag(view, id);
    }

    public static void setBackgroundTag(View view, @DrawableRes int id) {
        view.setTag(R.id.tagview_view_background, getResourceName(view.getContext(), id));
    }

    public static String getResourceName(Context context, @AnyRes int id) {
        if (id == 0) {
            return ViewTagStubs.INVALID_TAG;
        }
        Resources resources = context.getResources();
        return resources.getResourceTypeName(id) + "/" + resources.getResourceEntryName(id);
    }

    public static String getTag(View view, int key) {
        Object tag = view.getTag(key);
        if (tag != null && tag instanceof String) {
            return ((String) tag);
        }
        return ViewTagStubs.INVALID_TAG;
    }

    public static boolean compareResourceTags(View view, int key, @AnyRes int expectedResId) {
        String tag = getTag(view, key);
        return tag.equals(getResourceName(view.getContext(), expectedResId));
    }

    public static void clearBackground(View view) {
        view.setBackground(null);
        view.setTag(R.id.tagview_view_background, ViewTagStubs.INVALID_TAG);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setForeground(View view, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || view instanceof FrameLayout) {
            Context context = view.getContext();
            view.setForeground(getDrawable(context, id));
            setForegroundTag(view, id);
        }
    }

    public static void setForegroundTag(View view, @DrawableRes int id) {
        view.setTag(R.id.tagview_view_foreground, getResourceName(view.getContext(), id));
    }

    @Nullable
    private static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (id == 0) return null;
        return ContextCompat.getDrawable(context, id);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void clearForeground(View view) {
        view.setForeground(null);
        view.setTag(R.id.tagview_view_foreground, ViewTagStubs.INVALID_TAG);
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setTextViewCompoundDrawablesRelativeWithIntrinsicBounds(TextView view,
                                                                               @DrawableRes int left,
                                                                               @DrawableRes int top,
                                                                               @DrawableRes int right,
                                                                               @DrawableRes int bottom) {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom);
        setDrawableLeftTag(view, left);
        setDrawableTopTag(view, top);
        setDrawableRightTag(view, right);
        setDrawableBottomTag(view, bottom);
    }

    public static void setDrawableLeftTag(@NonNull TextView view, @DrawableRes int drawable) {
        view.setTag(R.id.tagview_textview_drawable_left, getResourceName(view.getContext(), drawable));
    }

    public static void setDrawableTopTag(@NonNull TextView view, @DrawableRes int drawable) {
        view.setTag(R.id.tagview_textview_drawable_top, getResourceName(view.getContext(), drawable));
    }

    public static void setDrawableRightTag(@NonNull TextView view, @DrawableRes int drawable) {
        view.setTag(R.id.tagview_textview_drawable_right, getResourceName(view.getContext(), drawable));
    }

    public static void setDrawableBottomTag(@NonNull TextView view, @DrawableRes int drawable) {
        view.setTag(R.id.tagview_textview_drawable_bottom, getResourceName(view.getContext(), drawable));
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
        setDrawableLeftTag(view, left);
        setDrawableTopTag(view, top);
        setDrawableRightTag(view, right);
        setDrawableBottomTag(view, bottom);
    }

    public static void setImageViewResource(ImageView view, @DrawableRes int id) {
        view.setBackgroundResource(id);
        setImageViewResourceTag(view, id);
    }

    public static void setImageViewResourceTag(ImageView view, @DrawableRes int id) {
        view.setTag(R.id.tagview_imageview_src, getResourceName(view.getContext(), id));
    }

    public static void clearImageViewResource(ImageView view) {
        view.setImageDrawable(null);
        view.setTag(R.id.tagview_imageview_src, ViewTagStubs.INVALID_TAG);
    }
}
