package com.shuhart.tagview.sample;

import android.content.Context;
import android.support.annotation.AnyRes;
import android.support.test.InstrumentationRegistry;
import android.view.View;

import com.shuhart.tagview.TagViewUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ViewTagMatchers {
    /**
     * Returns a matcher that matches {@link View}s based on tag keys and expected resource id values.
     *
     * @param key to match
     */
    public static Matcher<View> withTagKeyValue(final int key, final @AnyRes int expectedResId) {
        return new WithTagKeyValueMatcher(key, expectedResId);
    }

    static final class WithTagKeyValueMatcher extends TypeSafeMatcher<View> {
        private final int key;
        private final int expectedResId;

        private WithTagKeyValueMatcher(int key, @AnyRes int expectedResId) {
            this.key = key;
            this.expectedResId = expectedResId;
        }

        @Override
        public void describeTo(Description description) {
            Context context = InstrumentationRegistry.getTargetContext();
            description.appendText("with key: " + TagViewUtils.getResourceName(context, key));
            description.appendText(", with resource id: " + TagViewUtils.getResourceName(context, expectedResId));
        }

        @Override
        public boolean matchesSafely(View view) {
            Object tag = view.getTag(key);
            return (tag != null && tag instanceof String && tag.equals(TagViewUtils.getTag(view, key)));
        }
    }
}
