# TagView
Assert a view resources in your UI tests in easy way.

An android widget like TextView usually do not hold a reference to the drawable resources you set to them. It can be a trouble if you want to assert that view displays correct drawables. You can do that in several ways: compare raw bitmaps (bitmap.sameAs()) or set a custom tag with resource id to retrieve it later in your tests. This library can help you to automate the second approach.   

Approach here based on using a custom inflater. Pretty much the same as [Calligraphy](https://github.com/chrisjenx/Calligraphy) does. Some corresponding tags are set to the inflated by the [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html) views.

## Getting started

### Dependency

1. Add jcenter() to repositories block in your gradle file.
2. Add `implementation 'com.shuhart.tagview:tagview:0.2'` to your dependencies.

### Usage

Configure the library in your Application class:

```java
TagViewConfig.initDefault(new TagViewConfig.Builder()
        .build());	
```

Wrap the `Activity` Context

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(TagViewContextWrapper.wrap(newBase));
}
```

Thats it! Now you can retrieve some useful tags in your Espresso tests:

```java
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
```

You can find usage example in [ExampleInstrumentedTest](../blob/master/tagview/src/androidTest/java/com/shuhart/tagview/ExampleInstrumentedTest.java)

A convenient espresso matcher [WithTagKeyValueMatcher](../blob/master/tagview/src/androidTest/java/com/shuhart/tagview/ViewTagMatchers.java) is available.

### Tagging in runtime
If you create a widget in runtime not using the xml inflation then you can use [TagViewUtils](../TagView/blob/master/tagview/src/main/java/com/shuhart/tagview/TagViewUtils.java) when you want to set a TextView drawable or else:

```java
setBackground(View view, int id)
setBackgroundTag(View view, int id)
clearBackground(View view)
setForeground(View view, int id)
setForegroundTag(View view, int id)
clearForeground(View view)
setTextViewCompoundDrawablesRelativeWithIntrinsicBounds(TextView view, int left, int top, int right, int bottom)
setTextViewCompoundDrawables(TextView view, int left, int top, int right, int bottom)
setDrawableLeftTag(TextView view, int drawable)
setDrawableTopTag(TextView view, int drawable)
setDrawableRightTag(TextView view, int drawable)
setDrawableBottomTag(TextView view, int drawable)
setImageViewResource(ImageView view, int id)
```

### Supported tags

| Tag key (R.id) | Description | Usage |
|-----------------------|-----------------------|
| tagview_view_background | ```android:backgrond``` | TagViewUtils.getTag(view, ViewTag.VIEW_BACKGROUND.id) |
| tagview_view_foreground | ```android:foreground``` | TagViewUtils.getTag(view, ViewTag.VIEW_FOREGROUND.id) |
| tagview_textview_drawable_left | TextView ```android:drawableLeft or andorid:drawableStart``` | TagViewUtils.getTag(view, ViewTag.TEXTVIEW_DRAWABLE_LEFT.id) |
| tagview_textview_drawable_top | TextView ```android:drawableTop``` | TagViewUtils.getTag(view, ViewTag.TEXTVIEW_DRAWABLE_TOP.id) |
| tagview_textview_drawable_right | TextView ```android:drawableRight or android:drawableEnd``` | TagViewUtils.getTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id) |
| tagview_textview_drawable_bottom | TextView ```android:drawableBottom``` | TagViewUtils.getTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id) |
| tagview_imageview_src | ImageView ```android:src``` | TagViewUtils.getTag(view, ViewTag.TEXTVIEW_DRAWABLE_RIGHT.id) |


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