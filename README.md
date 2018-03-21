# TagView
Assert a view resources in your UI tests in easy way.

An android widget like TextView usually do not hold a reference to the drawable resources you set to them. It can be a trouble if you want to assert that view displays correct drawables. You can do that in several ways: compare raw bitmaps `bitmap.sameAs()` or set a custom tag with resource id to retrieve it later in your tests. This library can help you to automate the second approach.   

Approach here based on using a custom inflater. Pretty much the same as [Calligraphy](https://github.com/chrisjenx/Calligraphy) does. Some predefined tags are set to the inflated by the custom [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html) views.

## Getting started

### Dependency

1. Add jcenter() to repositories block in your gradle file.
2. Add `implementation 'com.shuhart.tagview:tagview:0.2'` to your dependencies.

### Usage

1. Configure the library in your Application class:

```java
TagViewConfig.initDefault(new TagViewConfig.Builder()
        .build());	
```

2. Wrap the `Activity` Context

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(TagViewContextWrapper.wrap(newBase));
}
```

3. Thats it! Now you can retrieve some useful tags in your Espresso tests:

```java
onView(withId(R.id.textview)).check(assertTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_LEFT.id, android.R.drawable.ic_delete));
onView(withId(R.id.textview)).check(assertTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_TOP.id, android.R.drawable.ic_btn_speak_now));
onView(withId(R.id.textview)).check(assertTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id, android.R.drawable.ic_input_add));
onView(withId(R.id.textview)).check(assertTagKeyValue(ViewTag.TEXTVIEW_DRAWABLE_BOTTOM.id, android.R.drawable.ic_input_get));
onView(withId(R.id.frame)).check(assertTagKeyValue(ViewTag.VIEW_BACKGROUND.id, R.color.colorPrimary));
onView(withId(R.id.frame)).check(assertTagKeyValue(ViewTag.VIEW_FOREGROUND.id, getSelectableItemBackgroundId(context)));
onView(withId(R.id.imageview)).check(assertTagKeyValue(ViewTag.IMAGEVIEW_SRC.id, android.R.drawable.ic_media_play));
```

You can find usage example in [ExampleInstrumentedTest](../blob/master/tagview/src/androidTest/java/com/shuhart/tagview/ExampleInstrumentedTest.java)

A convenient espresso matcher and assertion [ViewTagMatchers](../blob/master/tagview/src/androidTest/java/com/shuhart/tagview/ViewTagMatchers.java) are used.

### Tagging in runtime
If you create a widget in runtime not using the xml inflation then you can use [TagViewUtils](../TagView/blob/master/tagview/src/main/java/com/shuhart/tagview/TagViewUtils.java) when you want to set a TextView drawable or else:

```java
setBackground(View view, int id)
clearBackground(View view)
setForeground(View view, int id)
clearForeground(View view)
setTextViewCompoundDrawablesRelativeWithIntrinsicBounds(TextView view, int left, int top, int right, int bottom)
setTextViewCompoundDrawables(TextView view, int left, int top, int right, int bottom)
setImageViewResource(ImageView view, int id)
setTag(View view, int key, int id)
```

### Predefined tags

| Tag key (R.id) | Description |
|-----------------------|-----------------------|
| tagview_view_background | ```android:backgrond``` |
| tagview_view_foreground | ```android:foreground``` |
| tagview_textview_drawable_left | TextView ```android:drawableLeft or andorid:drawableStart``` |
| tagview_textview_drawable_top | TextView ```android:drawableTop``` |
| tagview_textview_drawable_right | TextView ```android:drawableRight or android:drawableEnd``` |
| tagview_textview_drawable_bottom | TextView ```android:drawableBottom``` |
| tagview_imageview_src | ImageView ```android:src``` |

You can retrieve any tag by calling `TagViewUtils.getTag(View view, @IdRes int key)`:
```java
TagViewUtils.getTag(view, ViewTag.VIEW_BACKGROUND.id)
```


### Custom attributes
If you want to support this feature for your custom views or any other not mentioned andorid widget attributes you can do it:

1. Add you custom attribute and a tag key for it in ```TagViewConfig```:

```java
TagViewConfig.initDefault(new TagViewConfig.Builder()
        .addAttributeTag(com.rengwuxian.materialedittext.R.attr.met_iconLeft, R.id.met_icon_left)
        .build());
```

2. UI test:

```java
onView(withId(R.id.met_edittext)).check(assertTagKeyValue(R.id.met_icon_left, android.R.drawable.ic_secure));
```

Any tag key for a view must be defined as id in xml. This is Android SDK requirement. See android.view.View#[setTag(int, Object)](https://developer.android.com/reference/android/view/View.html#setTag(int,%20java.lang.Object))


### No reflection way
If you for some reason do not want the library use reflection to inject a custom layout inflation factory you can use another approach. This could come in handy if you use some other libraries to override views inflation mechanism (like [Calligraphy](https://github.com/chrisjenx/Calligraphy)):

1. You need to disable reflection:
```java
TagViewConfig.initDefault(new TagViewConfig.Builder()
        .addAttributeTag(com.rengwuxian.materialedittext.R.attr.met_iconLeft, R.id.met_icon_left)
        .disablePrivateFactoryInjection() // no private factory injection takes place
        .build());
```

2. Then you need to override Activity#[onCreateView(String, Context, AttributeSet)](https://developer.android.com/reference/android/app/Activity.html#onCreateView(android.view.View,%20java.lang.String,%20android.content.Context,%20android.util.AttributeSet)) and delegate a view created by system inflater to the library:

```java
@Override
public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    View view = super.onCreateView(parent, name, context, attrs);
    return TagViewContextWrapper.onActivityCreateView(this, parent, view, name, context, attrs);
}
```


License
=======

    Copyright 2018 Bogdan Kornev.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
