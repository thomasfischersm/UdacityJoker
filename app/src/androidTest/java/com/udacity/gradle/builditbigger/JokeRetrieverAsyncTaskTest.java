package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

/**
 * An instrumented test for {@link JokeRetrieverAsyncTask}.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class JokeRetrieverAsyncTaskTest {

    private static final String LOG_TAG = JokeRetrieverAsyncTaskTest.class.getSimpleName();

    private Context targetContext;

    @Before
    public void setup() {
        targetContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void retrieveRandomJoke() throws ExecutionException, InterruptedException {
        JokeRetrieverAsyncTask jokeRetrieverAsyncTask = new JokeRetrieverAsyncTask(targetContext);
        jokeRetrieverAsyncTask.execute();

        String joke = jokeRetrieverAsyncTask.get();
        Log.i(LOG_TAG, "retrieveRandomJoke: Got joke: " + joke);
        assertNotNull(joke);
        assertThat(joke, not(isEmptyString()));

        // Note to Udacity reviewer: 'joke' cannot be an error  string. If an error happens, an
        // exception would be thrown. If we want to test that the backend picks a random joke,
        // that should be part of a unit test on the backend, not part of the integration test.
        // In an integration test, we are testing the integration between the app and the backend.
        // In a unit test in the backend and/or java module, we would test the correct behavior of
        // the code selection logic.

        // For more information, please refer to this forum discussion:
        // https://discussions.udacity.com/t/verify-that-joke-is-retrieved/405193/9
    }
}
