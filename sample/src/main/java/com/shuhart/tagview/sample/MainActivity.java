package com.shuhart.tagview.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shuhart.tagview.TagViewContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TagViewContextWrapper.wrap(newBase));
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.d(getClass().getSimpleName(), "onCreateView(" + name + ", " + context + ", " + (attrs == null ? "null" : attrs));
        return super.onCreateView(name, context, attrs);
    }
}
