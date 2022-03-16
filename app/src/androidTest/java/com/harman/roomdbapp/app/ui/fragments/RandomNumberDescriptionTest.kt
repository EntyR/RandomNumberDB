package com.harman.roomdbapp.app.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomNumberDescriptionTest {

    lateinit var activityScenario: ActivityScenario<MainActivity>

    private lateinit var spy: FragmentManager
    val value = 15

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).onActivity {
            spy = it.supportFragmentManager
            setUpFragment(it.supportFragmentManager, RandomNumbersList.newInstance())
            navigateTo(spy, RandomNumberDescription.newInstance(value), "go_to_description")
        }
    }

    @Test
    fun testBackButtonIsPressed() {
        onView(withId(R.id.btBack)).perform(click())
        assertThat(spy.backStackEntryCount).isEqualTo(0)
    }

    @Test
    fun testNumberInDescriptionIsSameAsInArgument() {
        onView(withId(R.id.tvNumberValue)).check(matches(withSubstring("15")))
    }

    @Test
    fun testNumberDescriptionInDescriptionIsCorrect() {
        onView(withId(R.id.tvNumberDescription)).check(matches(withText(R.string.number_not_even)))
    }

    private fun setUpFragment(
        parentFragmentManager: FragmentManager,
        fragmentToNavigate: Fragment
    ) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainerView, fragmentToNavigate)
        transaction.commitAllowingStateLoss()
    }

    private fun navigateTo(
        parentFragmentManager: FragmentManager,
        fragmentToNavigate: Fragment,
        transactionName: String
    ) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragmentToNavigate)
        transaction.addToBackStack(transactionName)
        transaction.commitAllowingStateLoss()
    }
}
