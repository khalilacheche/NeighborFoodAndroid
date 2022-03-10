package com.epfl.neighborfood.neighborfoodandroid;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.epfl.neighborfood.neighborfoodandroid.MainActivity.EXTRA_MESSAGE;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DisplayMessageActivityTest {
    @Test
    public void greetingMessageTest(){
        String intentString = "NeighborFoodTeam";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GreetingActivity.class);
        intent.putExtra(EXTRA_MESSAGE, intentString);

        ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent);
        onView(withId(R.id.textView2)).check(matches(withText(intentString)));
        scenario.close();

    }




}
