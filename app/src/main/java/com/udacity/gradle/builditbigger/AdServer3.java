package com.udacity.gradle.builditbigger;

import android.app.Activity;

/**
 * A class that can serve ads. There are different class versions in paid and free, so that the
 * paid version can be free of the dependency on the ads library.
 */
class AdServer3 implements AdServerInterface {

    public void onCreateViewFragment(Activity activity) {
        throw new IllegalStateException(
                "This should never be called! See the product flavor version.");
    }

    public void onResume(Activity activity) {
        throw new IllegalStateException(
                "This should never be called! See the product flavor version.");
    }

    public void serveInterstitial(Activity activity) {
        throw new IllegalStateException(
                "This should never be called! See the product flavor version.");
    }
}
