package com.epfl.neighborfood.neighborfoodandroid.ui.layouts;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

import android.Manifest;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;

import com.epfl.neighborfood.neighborfoodandroid.R;
import com.epfl.neighborfood.neighborfoodandroid.ui.activities.PlaceMealActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class PlaceMealMenuTest {
    @Rule
    public ActivityScenarioRule<PlaceMealActivity> testRule = new ActivityScenarioRule<>(PlaceMealActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    private View decorView;

    @Before
    public void setUp(){
        Intents.init();
        testRule.getScenario().onActivity(new ActivityScenario.ActivityAction<PlaceMealActivity>() {
            @Override
            public void perform(PlaceMealActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }
    @After
    public void cleanup() {
        Intents.release();
    }
    /*
     * Check if titles are visible.
     */
    @Test
    public void titlesAreVisibleTest(){
        onView(withId(R.id.MealNameTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.MealDescriptionTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.AllergensTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.DateTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.TimeTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.MealNameTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.MealNameTitle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    /*
     * Check meal Name changes correctly
     */
    @Test
    public void changeMealNameTest(){
        onView(withId(R.id.textMealName)).perform(typeText("pizza"));
        onView((withId(R.id.textMealName))).check(matches(withText("pizza")));
    }

    /*
     * Check meal Name changes correctly
     */
    @Test
    public void changeMealDescriptionTest(){
        onView(withId(R.id.textDesciption)).perform(typeText("vegan pizza"));
        onView((withId(R.id.textDesciption))).check(matches(withText("vegan pizza")));
    }

    /*
     * Check Price changes correctly
     */
    @Test
    public void changePriceTest(){
        onView(withId(R.id.textPrice)).perform(typeText("5"));
        onView((withId(R.id.textPrice))).check(matches(withText("5")));
    }

    /*
     * Check Time changes correctly
     */
    @Test
    public void changeTimeTest(){
        onView(withId(R.id.TimeText)).perform(typeText("15:00"));
        onView((withId(R.id.TimeText))).check(matches(withText("15:00")));
    }
    /*
     * Check Date changes correctly when done manually.
     */
    @Test
    public void changeDateManuallyTest(){
        onView(withId(R.id.DateText)).perform(typeText("21/03/2022"));
        onView((withId(R.id.DateText))).check(matches(withText("21/03/2022")));
    }
    /*
     * Check Date changes automatically when selected in calendar.
     */
    @Test
    public void changeDateByCalendarTest(){
        onView(withId(R.id.CalendarButton)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2022,11,11));
        onView(withId(android.R.id.button1)).perform(click());
        onView((withId(R.id.DateText))).check(matches(withText("11/11/2022")));

    }
    @Test
    public void testAddAndDeleteAllergen() {
        //add
        onView(withId(R.id.FishIcon)).perform(click());
        //delete
        onView(withId(R.id.FishIcon)).perform(click());

        onView(withId(R.id.CheeseIcon)).perform(click());
        onView(withId(R.id.ConfirmationButton)).perform(click());
        //missing check if FishIcon is disabled and CheeseIcon is enabled
    }
}
