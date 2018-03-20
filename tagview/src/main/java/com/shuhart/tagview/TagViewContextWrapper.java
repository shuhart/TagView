package com.shuhart.tagview;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class TagViewContextWrapper extends ContextWrapper {

    private TagViewLayoutInflater inflater;

    /**
     * Uses the default configuration from {@link TagViewConfig}
     *
     * Remember if you are defining default in the
     * {@link TagViewConfig} make sure this is initialised before
     * the activity is created.
     *
     * @param base ContextBase to Wrap.
     * @return ContextWrapper to pass back to the activity.
     */
    public static ContextWrapper wrap(Context base) {
        return new TagViewContextWrapper(base);
    }

    /**
     * You only need to call this <b>IF</b> you call
     * {@link TagViewConfig.Builder#disablePrivateFactoryInjection()}
     * This will need to be called from the
     * {@link Activity#onCreateView(View, String, Context, AttributeSet)}
     * method to enable view font injection if the view is created inside the activity onCreateView.
     *
     * You would implement this method like so in you base activity.
     * <pre>
     * {@code
     * public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
     *   return CalligraphyContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
     * }
     * }
     * </pre>
     *
     * @param activity The activity the original that the ContextWrapper was attached too.
     * @param parent   Parent view from onCreateView
     * @param view     The View Created inside onCreateView or from super.onCreateView
     * @param name     The View name from onCreateView
     * @param context  The context from onCreateView
     * @param attr     The AttributeSet from onCreateView
     * @return The same view passed in, or null if null passed in.
     */
    public static View onActivityCreateView(Activity activity, View parent, View view, String name, Context context, AttributeSet attr) {
        return get(activity).onActivityCreateView(parent, view, name, context, attr);
    }

    /**
     * Get the Calligraphy Activity Fragment Instance to allow callbacks for when views are created.
     *
     * @param activity The activity the original that the ContextWrapper was attached too.
     * @return Interface allowing you to call onActivityViewCreated
     */
    static TagViewActivityFactory get(Activity activity) {
        if (!(activity.getLayoutInflater() instanceof TagViewLayoutInflater)) {
            throw new RuntimeException("This activity does not wrap the Base Context! See CalligraphyContextWrapper.wrap(Context)");
        }
        return (TagViewActivityFactory) activity.getLayoutInflater();
    }

    /**
     * Uses the default configuration from {@link TagViewConfig}
     *
     * Remember if you are defining default in the
     * {@link TagViewConfig} make sure this is initialised before
     * the activity is created.
     *
     * @param base ContextBase to Wrap
     */
    TagViewContextWrapper(Context base) {
        super(base);
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (inflater == null) {
                inflater = new TagViewLayoutInflater(LayoutInflater.from(getBaseContext()), this, false);
            }
            return inflater;
        }
        return super.getSystemService(name);
    }

}