package com.kevinkerin.weather_app

import activity.MainActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get: Rule
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_activity_inView() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_sorting_buttons() {
        onView(withId(R.id.a_to_z_button)).check(matches(isDisplayed()))
        onView(withId(R.id.temp_button)).check(matches(isDisplayed()))
        onView(withId(R.id.last_updated_button)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isItemClickable() {
        onView(withId(R.id.recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
    }

    @Test
    fun test_recyclerView() {
        val recyclerView: RecyclerView = activityTestRule.activity.findViewById(R.id.recycler_view)

        val itemCount: Int? = recyclerView.adapter?.itemCount

        if (itemCount != null) {
            onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    itemCount - 1
                )
            )
        }
    }

}