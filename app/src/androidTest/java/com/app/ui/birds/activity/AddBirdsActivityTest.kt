package com.app.ui.birds.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.app.roomkoin.R
import com.app.utils.ConstantsTest.Companion.BIRD_TEST_NAME
import com.app.utils.ConstantsTest.Companion.BIRD_TEST_NOTES
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.app.utils.ConstantsTest.Companion.SPINNER_SELECTION_TEXT
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.allOf

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddBirdsActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<AddBirdsActivity>
            = ActivityTestRule(AddBirdsActivity::class.java)

    @Test
    fun setBirdEntityData_ExitTheScreen() {

        // Type text Bird name and then close Keyboard.
        onView(withId(R.id.edt_name))
            .perform(typeText(BIRD_TEST_NAME), closeSoftKeyboard())

        // Click on Spinner and select the "Rare" Text from String Character Array
        onView(withId(R.id.spinner_rarity)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(SPINNER_SELECTION_TEXT))).perform(click())
        onView(withId(R.id.spinner_rarity)).check(matches(withSpinnerText(containsString(SPINNER_SELECTION_TEXT))))

        // Type text Bird notes and then close Keyboard.
        onView(withId(R.id.edt_notes))
            .perform(typeText(BIRD_TEST_NOTES), closeSoftKeyboard())

        // Click on Cancel Button.
        onView(withId(R.id.btn_cancel)).perform(click())
    }
}