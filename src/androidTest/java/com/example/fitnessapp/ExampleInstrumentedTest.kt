package com.example.fitnessapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.fitnessapp.controller.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.fitnessapp", appContext.packageName)
    }

    @Test
    fun main_page_is_displayed(){
        onView(withText("STARTEN")).check(matches(isDisplayed()))
    }

    @Test
    fun uebungs_aktivitaet_is_displayed(){
        onView(withId(R.id.idStarten)).perform(click())
        onView(withText("Machen Sie sich Bereit")).check(matches(isDisplayed()))
    }

    @Test
    fun first_exercise_started(){
        onView(withId(R.id.idStarten)).perform(click())
        Thread.sleep(4000) //wait 4 seconds
        onView(withText("Hampelmann")).check(matches(isDisplayed()))
    }
    @Test
    fun second_exercise_started(){
        first_exercise_started()
        Thread.sleep(7000) //waits 7 seconds.. Rest time + first Ex. time
        onView(withText("Kniebeugen")).check(matches(isDisplayed()))
    }
    @Test
    fun third_exercise_started(){
        second_exercise_started()
        Thread.sleep(7000)
        onView(withText("Beine Übung")).check(matches(isDisplayed()))
    }
    @Test
    fun fourth_exercise_started(){
        third_exercise_started()
        Thread.sleep(7000)
        onView(withText("Brustmuskeltraining Pushups")).check(matches(isDisplayed()))
    }
    @Test
    fun fifth_exercise_started(){
        fourth_exercise_started()
        Thread.sleep(7000)
        onView(withText("Untere Brustmuskeltraining Pushups")).check(matches(isDisplayed()))
    }
    @Test
    fun sixth_exercise_started(){
        fifth_exercise_started()
        Thread.sleep(7000)
        onView(withText("Trizepdips")).check(matches(isDisplayed()))
    }
    @Test
    fun seventh_exercise_started(){
        sixth_exercise_started()
        Thread.sleep(7000)
        onView(withText("Trizepsstrecker")).check(matches(isDisplayed()))
    }
    @Test
    fun eighth_exercise_started(){
        seventh_exercise_started()
        Thread.sleep(7000)
        onView(withText("Waschbrettbauch")).check(matches(isDisplayed()))
    }
    @Test
    fun ninth_exercise_started(){
        eighth_exercise_started()
        Thread.sleep(7000)
        onView(withText("Unterarmstütz")).check(matches(isDisplayed()))
    }

    @Test
    fun user_click_on_start_button(){
        //onView(withId(R.id.llStart)).perform(typeText("Machen Sie sich bereit"))
        onView(withId(R.id.idStarten)).perform(click())
        onView(withId(R.id.idRestViewText)).check(matches(withText("Machen Sie sich Bereit")))
    }

    @Test
    fun after_ten_seconds_pass_by(){

    }
}
