package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.playposse.jokeactivitylibrary.JokeActivity;
import com.playposse.udacityjoker.backend.jokeEndpoint.JokeEndpoint;
import com.playposse.udacityjoker.backend.jokeEndpoint.model.JokeBean;
import com.udacity.gradle.builditbigger.retrievaltool.JokeGenerator;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static JokeEndpoint jokeEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JokeGenerator.retrieveJokes();
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
//        Toast.makeText(this, joke, Toast.LENGTH_LONG).show();
        new JokeRetrieverAsyncTask().execute();
    }

    /**
     * An {@link AsyncTask} that retrieves a random joke from the cloud backend.
     */
    private class JokeRetrieverAsyncTask extends AsyncTask<Void, Void, String> {

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
            Intent intent = new Intent(MainActivity.this, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_EXTRA, joke);
            startActivity(intent);
        }
    }
}
