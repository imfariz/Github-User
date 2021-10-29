package com.dicoding.submission1_fundamentalandroid

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyLogin = "sidiqpermana"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun findList() {
        onView(withId(R.id.edUser)).perform(typeText(dummyLogin), closeSoftKeyboard())
        onView(withId(R.id.btnUser)).perform(click())

        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
    }
}