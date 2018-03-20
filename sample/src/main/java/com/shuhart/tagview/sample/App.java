package com.shuhart.tagview.sample;

import android.app.Application;

import com.shuhart.tagview.TagViewConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TagViewConfig.initDefault(new TagViewConfig.Builder()
                .build());
    }
}
