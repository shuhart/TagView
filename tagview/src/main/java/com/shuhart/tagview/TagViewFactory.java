package com.shuhart.tagview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class TagViewFactory {

    public View onViewCreated(@Nullable View view, Context context, AttributeSet attrs) {
        if (view != null && view.getTag(R.id.tagview_tag_id) != Boolean.TRUE) {
            onViewCreatedInternal(view, context, attrs);
            view.setTag(R.id.tagview_tag_id, Boolean.TRUE);
        }
        return view;
    }

    void onViewCreatedInternal(View view, final Context context, AttributeSet attrs) {
        int backgroundId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_BACKGROUND);
        if (backgroundId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setBackgroundTag(view, backgroundId);
        }
        int foregroundId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_FOREGROUND);
        if (foregroundId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setForegroundTag(view, foregroundId);
        }
        if (view instanceof TextView) {
            onTextViewCreated((TextView) view, context, attrs);
        } else if (view instanceof ImageView) {
            onImageViewCreated(((ImageView) view), context, attrs);
        }
    }

    private void onTextViewCreated(TextView view, Context context, AttributeSet attrs) {
        int drawableLeftId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_LEFT);
        if (drawableLeftId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setDrawableLeftTag(view, drawableLeftId);
        } else {
            drawableLeftId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_START);
            if (drawableLeftId != ViewTags.EMPTY_RESOURCE) {
                TagViewUtils.setDrawableLeftTag(view, drawableLeftId);
            }
        }
        int drawableTopId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_TOP);
        if (drawableTopId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setDrawableTopTag(view, drawableTopId);
        }
        int drawableRightId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_RIGHT);
        if (drawableRightId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setDrawableRightTag(view, drawableRightId);
        } else {
            drawableRightId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_END);
            if (drawableRightId != ViewTags.EMPTY_RESOURCE) {
                TagViewUtils.setDrawableLeftTag(view, drawableRightId);
            }
        }
        int drawableBottomId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_BOTTOM);
        if (drawableBottomId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setDrawableBottomTag(view, drawableBottomId);
        }
    }

    private void onImageViewCreated(ImageView view, Context context, AttributeSet attrs) {
        int srcId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_SRC);
        if (srcId != ViewTags.EMPTY_RESOURCE) {
            TagViewUtils.setImageViewResourceTag(view, srcId);
        }
    }
}