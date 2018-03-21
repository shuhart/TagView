package com.shuhart.tagview.sample;

import android.app.Application;

import com.shuhart.tagview.TagViewConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TagViewConfig.initDefault(new TagViewConfig.Builder()
                .addAttributeTag(com.rengwuxian.materialedittext.R.attr.met_iconRight, R.id.met_icon_right)
                .build());
    }
}
