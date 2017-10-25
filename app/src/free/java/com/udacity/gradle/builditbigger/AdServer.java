package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

class AdServer implements AdServerInterface {

    private static final String LOG_TAG = AdServer.class.getSimpleName();

    private InterstitialAd interstitialAd;

    public void onCreateViewFragment(Activity activity) {
        AdView mAdView = (AdView) activity.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    public void onResume(Activity activity) {
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void serveInterstitial(final Activity activity) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) activity).reallyTellJoke();
                        }
                    });
                }
            });
            interstitialAd.show();
        } else {
            Log.i(LOG_TAG, "serveInterstitial: Failed to load interstitial ad");
        }
    }
}
