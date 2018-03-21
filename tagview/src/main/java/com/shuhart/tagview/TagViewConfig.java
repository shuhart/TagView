package com.shuhart.tagview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;

public class TagViewConfig {

    private static TagViewConfig INSTANCE;

    /**
     * Set the default Calligraphy Config
     *
     * @param TagViewConfig the config build using the builder.
     * @see TagViewConfig.Builder
     */
    public static void initDefault(TagViewConfig TagViewConfig) {
        INSTANCE = TagViewConfig;
    }

    /**
     * The current Calligraphy Config.
     * If not set it will create a default config.
     */
    public static TagViewConfig get() {
        if (INSTANCE == null)
            INSTANCE = new TagViewConfig(new Builder());
        return INSTANCE;
    }

    /**
     * Use Reflection to inject the private factory.
     */
    public final boolean reflection;
    /**
     * Use Reflection to intercept CustomView inflation with the correct Context.
     */
    public final boolean customViewCreation;
    /**
     * Custom attributes to look for when a view is inflated.
     * Use it in case predefined attributes are not enough.
     */
    public final SparseIntArray customViewTags;

    protected TagViewConfig(Builder builder) {
        reflection = builder.reflection;
        customViewCreation = builder.customViewCreation;
        customViewTags = builder.customViewTags;
    }

    public static class Builder {
        /**
         * Use Reflection to intercept CustomView inflation with the correct Context.
         */
        private boolean customViewCreation = true;
        /**
         * Use Reflection to inject the private factory. Doesn't exist pre HC. so defaults to false.
         */
        private boolean reflection = true;
        /**
         * Custom attributes to look for when a view is inflated.
         * Use it in case predefined attributes are not enough.
         */
        private SparseIntArray customViewTags = new SparseIntArray();
        /**
         * <p>Turn of the use of Reflection to inject the private factory.
         * This has operational consequences! Please read and understand before disabling.
         * <b>This is already disabled on pre Honeycomb devices. (API 11)</b></p>
         *
         * <p> If you disable this you will need to override your {@link Activity#onCreateView(View, String, Context, AttributeSet)}
         * as this is set as the {@link LayoutInflater} private factory.</p>
         * <br>
         * <b> Use the following code in the Activity if you disable FactoryInjection:</b>
         * <pre><code>
         * {@literal @}Override
         * {@literal @}TargetApi(Build.VERSION_CODES.HONEYCOMB)
         * public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
         *   return CalligraphyContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
         * }
         * </code></pre>
         */
        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }

        /**
         * Due to the poor inflation order where custom views are created and never returned inside an
         * {@code onCreateView(...)} method. We have to create CustomView's at the latest point in the
         * overrideable injection flow.
         *
         * On HoneyComb+ this is inside the {@link Activity#onCreateView(View, String, Context, AttributeSet)}
         * Pre HoneyComb this is in the {@link LayoutInflater.Factory#onCreateView(String, AttributeSet)}
         *
         * We wrap base implementations, so if you LayoutInflater/Factory/Activity creates the
         * custom view before we get to this point, your view is used. (Such is the case with the
         * TintEditText etc)
         *
         * The problem is, the native methods pass there parents context to the constructor in a really
         * specific place. We have to mimic this in
         * {@link TagViewLayoutInflater#createCustomViewInternal(View, View, String, Context, AttributeSet)}
         * To mimic this we have to use reflection as the Class constructor args are hidden to us.
         *
         * We have discussed other means of doing this but this is the only semi-clean way of doing it.
         * (Without having to do proxy classes etc).
         *
         * Calling this will of course speed up inflation by turning off reflection, but not by much,
         * But if you want Calligraphy to inject the correct typeface then you will need to make sure your CustomView's
         * are created before reaching the LayoutInflater onViewCreated.
         */
        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        /**
         * Set a custom attribute to lookup in an inflated view.
         * You can use it for your custom views or any other android widgets.
         *
         * @param attr to search for in an inflated view
         * @param key to be used for tag in view. Value for a tag is a resource id.
         */
        public Builder addAttributeTag(@AttrRes int attr, @IdRes Integer key) {
            this.customViewTags.append(attr, key);
            return this;
        }

        public TagViewConfig build() {
            return new TagViewConfig(this);
        }

    }
}
