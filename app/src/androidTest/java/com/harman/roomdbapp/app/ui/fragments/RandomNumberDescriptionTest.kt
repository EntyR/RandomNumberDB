package com.harman.roomdbapp.app.ui.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.other.NUMBER_VALUE
import com.harman.roomdbapp.app.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class RandomNumberDescriptionTest {

    lateinit var activityScenario: ActivityScenario<MainActivity>

//    @get:Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var fragment: FragmentScenario<RandomNumberDescription>

    lateinit var spy: FragmentManager

    @Before
    fun setUp() {
        val bundle = bundleOf(NUMBER_VALUE to 1)
        fragment = launchFragmentInContainer<RandomNumberDescription>(bundle)

        fragment.withFragment {
            spy = spy(parentFragmentManager)
            fragment.withFragment {

            }
        }
    }

    @Test
    fun testBackButtonPressed() {
        doNothing().`when`(spy).popBackStack()

        onView(withId(R.id.btBack)).perform(click())
        verify(spy).popBackStack()
    }
}