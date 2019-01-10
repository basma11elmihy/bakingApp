package com.example.android.bakingappudacity;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import 	android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

//https://stackoverflow.com/questions/40014465/unable-to-import-static-android-support-test-espresso-contrib-drawermatchers-iso
//https://stackoverflow.com/questions/43886009/espressono-views-in-hierarchy-found-matching-with-id-androidid-home
@RunWith(AndroidJUnit4.class)
public class ButtonNameCheck {
    @Rule public ActivityTestRule<MainActivity> mActivity
            = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void setup() {
        mIdlingResource =  mActivity.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void click_button(){
        onView(ViewMatchers.withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Espresso.pressBack();
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null)
            Espresso.unregisterIdlingResources(mIdlingResource);
    }
}
