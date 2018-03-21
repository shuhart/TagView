package com.shuhart.tagview;

public enum ViewTag {
    VIEW_BACKGROUND(R.id.tagview_view_background),
    VIEW_FOREGROUND(R.id.tagview_view_foreground),
    TEXTVIEW_DRAWABLE_LEFT(R.id.tagview_textview_drawable_left),
    TEXTVIEW_DRAWABLE_TOP(R.id.tagview_textview_drawable_top),
    TEXTVIEW_DRAWABLE_RIGHT(R.id.tagview_textview_drawable_right),
    TEXTVIEW_DRAWABLE_BOTTOM(R.id.tagview_textview_drawable_bottom),
    IMAGEVIEW_SRC(R.id.tagview_imageview_src);

    ViewTag(int id) {
        this.id = id;
    }

    public int id;
}
