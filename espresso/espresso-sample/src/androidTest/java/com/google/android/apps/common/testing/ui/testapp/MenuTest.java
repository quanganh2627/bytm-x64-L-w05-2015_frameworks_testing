/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.common.testing.ui.testapp;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.longClick;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.pressMenuKey;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.doesNotExist;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isRoot;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * Ensures view root ordering works properly.
 */
@LargeTest
public class MenuTest extends ActivityInstrumentationTestCase2<MenuActivity> {
  @SuppressWarnings("deprecation")
  public MenuTest() {
    // This constructor was deprecated - but we want to support lower API levels.
    super("com.google.android.apps.common.testing.ui.testapp", MenuActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getActivity();
  }

  public void testPopupMenu() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      // popup menus are post honeycomb.
      return;
    }
    onView(withText(R.string.popup_item_1_text)).check(doesNotExist());
    onView(withId(R.id.popup_button)).perform(click());
    onView(withText(R.string.popup_item_1_text)).check(matches(isDisplayed())).perform(click());

    onView(withId(R.id.text_menu_result)).check(matches(withText(R.string.popup_item_1_text)));
  }

  public void testContextMenu() {
    onView(withText(R.string.context_item_2_text)).check(doesNotExist());
    onView(withId(R.id.text_context_menu)).perform(longClick());
    onView(withText(R.string.context_item_2_text)).check(matches(isDisplayed())).perform(click());

    onView(withId(R.id.text_menu_result)).check(matches(withText(R.string.context_item_2_text)));
  }

  public void testOptionMenu() {
    onView(withText(R.string.options_item_3_text)).check(doesNotExist());
    onView(isRoot()).perform(pressMenuKey());
    onView(withText(R.string.options_item_3_text)).check(matches(isDisplayed())).perform(click());

    onView(withId(R.id.text_menu_result)).check(matches(withText(R.string.options_item_3_text)));
  }
}
