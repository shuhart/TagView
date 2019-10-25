package com.shuhart.tagview.sample;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AnyRes;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
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
    public static Matcher<View> withTagKeyValue(final @IdRes int key, final @AnyRes int expectedResId) {
        return new WithTagKeyValueMatcher(key, expectedResId);
    }

    static final class WithTagKeyValueMatcher extends TypeSafeMatcher<View> {
        private final int key;
        private final int expectedResId;

        private WithTagKeyValueMatcher(@IdRes int key, @AnyRes int expectedResId) {
            this.key = key;
            this.expectedResId = expectedResId;
        }

        @Override
        public void describeTo(Description description) {
            Context context = InstrumentationRegistry.getTargetContext();
            Resources resources = context.getResources();
            description.appendText("with key: " + getResourceName(resources, key));
            description.appendText(", with resource id: " + getResourceName(resources, expectedResId));
        }

        private String getResourceName(Resources resources, int id) {
            return resources.getResourceTypeName(id) + "/" + resources.getResourceEntryName(id);
        }

        @Override
        public boolean matchesSafely(View view) {
            Object tag = view.getTag(key);
            return (tag instanceof Integer && tag.equals(TagViewUtils.getTag(view, key)));
        }
    }

    public static ViewAssertion assertTagKeyValue(final @IdRes int key, @AnyRes int expectedResId) {
        return ViewAssertions.matches(withTagKeyValue(key, expectedResId));
    }
}
