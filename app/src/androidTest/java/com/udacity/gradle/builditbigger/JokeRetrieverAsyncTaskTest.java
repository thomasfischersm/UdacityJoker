package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.playposse.jokeactivitylibrary.JokeActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * An instrumented test for {@link JokeRetrieverAsyncTask}.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class JokeRetrieverAsyncTaskTest {

    private static final String LOG_TAG = JokeRetrieverAsyncTaskTest.class.getSimpleName();

    private Context targetContext;
    private Instrumentation.ActivityMonitor activityMonitor;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Before
    public void setup() {
        targetContext = InstrumentationRegistry.getTargetContext();

        // The statement below requires a higher API version than is defined for the app. That's
        // okay because we control the environment in which this test is run.
        activityMonitor = new Instrumentation.ActivityMonitor();
        InstrumentationRegistry.getInstrumentation().addMonitor(activityMonitor);
    }

    @Test
    public void retrieveRandomJoke() throws ExecutionException, InterruptedException {
        JokeRetrieverAsyncTask jokeRetrieverAsyncTask = new JokeRetrieverAsyncTask(targetContext);
        jokeRetrieverAsyncTask.execute();

        // Wait for the AsyncTask to complete.
        // (Throw exception, so that JUnit can report it nicely.)
        jokeRetrieverAsyncTask.get();
        Activity lastActivity = activityMonitor.waitForActivity();

        // Verify that the right activity got started with a joke.
        assertEquals(JokeActivity.class, lastActivity.getClass());

        // Verify the joke is present.
        Intent lastIntent = lastActivity.getIntent();
        String joke = lastIntent.getStringExtra(JokeActivity.JOKE_EXTRA);
        assertNotNull(joke);
        Log.e(LOG_TAG, "retrieveRandomJoke: Got joke: " + joke);
    }
}
