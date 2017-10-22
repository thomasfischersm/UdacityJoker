package com.playposse.jokeactivitylibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * An {@link Activity} that shows a joke passed as an intent parameter.
 */
public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "joke";

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke);

        jokeTextView = findViewById(R.id.joke_text_view);
        String joke = getIntent().getStringExtra(JOKE_EXTRA);
        jokeTextView.setText(joke);
    }
}
