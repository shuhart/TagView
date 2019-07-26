package com.shuhart.tagview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

class TagViewFactory {

    public View onViewCreated(@Nullable View view, Context context, AttributeSet attrs) {
        if (view != null && view.getTag(R.id.tagview_tag_id) != Boolean.TRUE) {
            onViewCreatedInternal(view, context, attrs);
            view.setTag(R.id.tagview_tag_id, Boolean.TRUE);
        }
        return view;
    }

    void onViewCreatedInternal(View view, final Context context, AttributeSet attrs) {
        if (view instanceof TextView) {
            onTextViewCreated((TextView) view, context, attrs);
        } else if (view instanceof ImageView) {
            onImageViewCreated(((ImageView) view), context, attrs);
        }
        onAnyViewCreated(view, context, attrs);
    }

    private void onTextViewCreated(TextView view, Context context, AttributeSet attrs) {
        int drawableLeftId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_LEFT);
        if (drawableLeftId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, drawableLeftId);
        } else {
            drawableLeftId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_START);
            if (drawableLeftId != ViewTagStubs.EMPTY_RESOURCE) {
                TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, drawableLeftId);
            }
        }
        int drawableTopId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_TOP);
        if (drawableTopId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_TOP.id, drawableTopId);
        }
        int drawableRightId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_RIGHT);
        if (drawableRightId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, drawableRightId);
        } else {
            drawableRightId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_END);
            if (drawableRightId != ViewTagStubs.EMPTY_RESOURCE) {
                TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, drawableRightId);
            }
        }
        int drawableBottomId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_DRAWABLE_BOTTOM);
        if (drawableBottomId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.TEXTVIEW_DRAWABLE_BOTTOM.id, drawableBottomId);
        }
    }

    private void onImageViewCreated(ImageView view, Context context, AttributeSet attrs) {
        int srcId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_SRC);
        if (srcId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.IMAGEVIEW_SRC.id, srcId);
        }
    }

    private void onAnyViewCreated(View view, Context context, AttributeSet attrs) {
        pullPredefinedAttrs(view, context, attrs);
        pullCustomAttrs(view, context, attrs);
    }

    private void pullPredefinedAttrs(View view, Context context, AttributeSet attrs) {
        int backgroundId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_BACKGROUND);
        if (backgroundId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.VIEW_BACKGROUND.id, backgroundId);
        }
        int foregroundId = TagViewUtils.pullAttr(context, attrs, Attrs.ANDROID_ATTR_FOREGROUND);
        if (foregroundId != ViewTagStubs.EMPTY_RESOURCE) {
            TagViewUtils.setTag(view, ViewTag.VIEW_FOREGROUND.id, foregroundId);
        }
    }

    private void pullCustomAttrs(View view, Context context, AttributeSet attrs) {
        SparseIntArray tags = TagViewConfig.get().customViewTags;
        for (int i = 0; i < tags.size(); i++) {
            int attr = tags.keyAt(i);
            int id = TagViewUtils.pullAttr(context, attrs, new int[]{attr});
            if (id != ViewTagStubs.EMPTY_RESOURCE) {
                TagViewUtils.setTag(view, tags.get(attr), id);
            }
        }
    }
}