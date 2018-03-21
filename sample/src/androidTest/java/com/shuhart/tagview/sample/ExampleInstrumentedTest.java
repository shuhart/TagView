package com.shuhart.tagview.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shuhart.tagview.ViewTag;
import com.shuhart.tagview.ViewTagStubs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static com.shuhart.tagview.sample.ViewTagMatchers.withTagKeyValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test() {
        final Context context = InstrumentationRegistry.getTargetContext();
        onView(withId(R.id.textview)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, android.R.drawable.ic_delete)));
        onView(withId(R.id.textview)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_TOP.id, android.R.drawable.ic_btn_speak_now)));
        onView(withId(R.id.textview)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, android.R.drawable.ic_input_add)));
        onView(withId(R.id.textview)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_BOTTOM.id, android.R.drawable.ic_input_get)));
        onView(withId(R.id.frame)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.VIEW_BACKGROUND.id, R.color.colorPrimary)));
        onView(withId(R.id.frame)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.VIEW_FOREGROUND.id, getSelectableItemBackgroundId(context))));
        onView(withId(R.id.imageview)).check(ViewAssertions.matches(
                withTagKeyValue(ViewTag.IMAGEVIEW_SRC.id, android.R.drawable.ic_media_play)));
    }

    private int getSelectableItemBackgroundId(Context context) {
        int[] attrs = new int[] { android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int id = ta.getResourceId(0, 0);
        ta.recycle();
        return id;
    }
}
