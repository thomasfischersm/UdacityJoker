package com.udacity.gradle.builditbigger;

import android.app.Activity;

class AdServer implements AdServerInterface {

    private static final String LOG_TAG = AdServer.class.getSimpleName();

    public void onCreateViewFragment(Activity activity) {
        // Nothing to do for paid flavor.
    }

    public void onResume(Activity activity) {
        // Nothing to do for paid flavor.
    }

    public void serveInterstitial(Activity activity) {
        // Nothing to do for paid flavor.
    }
}
