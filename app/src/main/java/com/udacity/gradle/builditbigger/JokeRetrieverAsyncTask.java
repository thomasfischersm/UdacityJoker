package com.udacity.gradle.builditbigger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.playposse.jokeactivitylibrary.JokeActivity;
import com.playposse.udacityjoker.backend.jokeEndpoint.JokeEndpoint;
import com.playposse.udacityjoker.backend.jokeEndpoint.model.JokeBean;

import java.io.IOException;

/**
 * An {@link AsyncTask} that retrieves a random joke from the cloud backend.
 */
class JokeRetrieverAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = JokeRetrieverAsyncTask.class.getSimpleName();

    private static JokeEndpoint jokeEndpoint;

    private final Context context;

    JokeRetrieverAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (jokeEndpoint == null) {
            jokeEndpoint = new JokeEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request)
                                throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    }).build();
        }

        try {
            JokeEndpoint.GetRandomJoke call = jokeEndpoint.getRandomJoke();
            JokeBean jokeBean = call.execute();
            return jokeBean.getJoke();
        } catch (IOException ex) {
            Log.e(LOG_TAG, "doInBackground: Failed to communicate with the cloud.", ex);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.setComponent(new ComponentName("com.udacity.gradle.builditbigger", "com.playposse.jokeactivitylibrary.JokeActivity"));
        intent.putExtra(JokeActivity.JOKE_EXTRA, joke);
        context.startActivity(intent);
        Log.i(LOG_TAG, "onPostExecute: JokeRetrieverAsyncTask is complete.");
    }
}
