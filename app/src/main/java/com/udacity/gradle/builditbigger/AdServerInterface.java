package com.udacity.gradle.builditbigger;

import android.app.Activity;

/**
 * An interface for all the product flavor specific implementations of {@link AdServer} to
 * implement.
 */
public interface AdServerInterface {

    /**
     * Invoked by the Activity during onCreateViewFragment.
     */
    void onCreateViewFragment(Activity activity);

    /**
     * Invoked by the Activity during onResume.
     */
    void onResume(Activity activity);

    /**
     * Serves an interstitial.
     */
    void serveInterstitial(Activity activity);
}
