package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;

    private AdServer adServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        adServer = new AdServer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adServer.onResume(this);
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
        if (BuildConfig.FLAVOR.equals(ProductFlavor.free.name())) {
            adServer.serveInterstitial(this);
        } else {
            reallyTellJoke();
        }
    }

    void reallyTellJoke() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.invalidate();
        ((RelativeLayout) progressBar.getParent()).invalidate();

        new JokeRetrieverAsyncTask(this).execute();
    }

    AdServer getAdServer() {
        return adServer;
    }
}
