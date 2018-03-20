package com.shuhart.tagview.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuhart.tagview.TagViewUtils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
        onView(withId(R.id.textview)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException == null) {
                    TextView textView = (TextView) view;
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, android.R.drawable.ic_delete),
                            TagViewUtils.getTextViewDrawableLeftTag(textView));
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, android.R.drawable.ic_btn_speak_now),
                            TagViewUtils.getTextViewDrawableTopTag(textView));
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, android.R.drawable.ic_input_add),
                            TagViewUtils.getTextViewDrawableRightTag(textView));
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, android.R.drawable.ic_input_get),
                            TagViewUtils.getTextViewDrawableBottomTag(textView));
                }
            }
        });
        onView(withId(R.id.frame)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException == null) {
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, R.color.colorPrimary),
                            TagViewUtils.getBackgroundTag(view));
                    int id = getSelectableItemBackgroundId(context);
                    if (id != 0) {
                        Assert.assertEquals(
                                TagViewUtils.getResourceName(context, id),
                                TagViewUtils.getForegroundTag(view));
                    }
                }
            }
        });
        onView(withId(R.id.imageview)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException == null) {
                    Assert.assertEquals(
                            TagViewUtils.getResourceName(context, android.R.drawable.ic_media_play),
                            TagViewUtils.getImageViewResourceTag((ImageView) view));
                }
            }
        });
    }

    private int getSelectableItemBackgroundId(Context context) {
        int[] attrs = new int[] { android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int id = ta.getResourceId(0, 0);
        ta.recycle();
        return id;
    }
}
