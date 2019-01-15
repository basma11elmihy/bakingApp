package com.example.android.bakingappudacity;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class ButtonScrollCheck {

        @Rule
        public ActivityTestRule<MainActivity> mActivity
                = new ActivityTestRule<>(MainActivity.class);
        private IdlingResource mIdlingResource;

    @Before
    public void setup() {
        mIdlingResource =  mActivity.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);

    }
    @Test
    public void scrollTo_button(){
        onView(ViewMatchers.withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText(R.string.Cheesecake)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null)
            Espresso.unregisterIdlingResources(mIdlingResource);
    }



}
