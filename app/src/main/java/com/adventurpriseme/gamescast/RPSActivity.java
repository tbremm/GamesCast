package com.adventurpriseme.gamescast;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class RPSActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Cast.MessageReceivedCallback {

    static MediaRouter mMediaRouter;
    static MediaRouteSelector mMediaRouteSelector;
    static MediaRouter.Callback mMediaRouterCallback;
    static Cast.Listener mCastClientListener;
    static GoogleApiClient mApiClient;
    private static final String TAG = "RPS Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);

        mMediaRouter = MediaRouter.getInstance(getApplicationContext());

        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast("21E58E1F"))
                .build();

        mMediaRouterCallback = new MyMediaRouterCallback();

        mCastClientListener = new Cast.Listener() {
            @Override
            public void onApplicationStatusChanged() {
                if (mApiClient != null) {
                    Log.d(TAG, "onApplicationStatusChanged: "
                            + Cast.CastApi.getApplicationStatus(mApiClient));
                }
            }

            @Override
            public void onVolumeChanged() {
                if (mApiClient != null) {
                     Log.d(TAG, "onVolumeChanged: " + Cast.CastApi.getVolume(mApiClient));
                }
            }

            @Override
            public void onApplicationDisconnected(int errorCode) {
                // fixme what is this? teardown();
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.r, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onPause() {
        if (isFinishing()) {
            mMediaRouter.removeCallback(mMediaRouterCallback);
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onStop() {
        mMediaRouter.removeCallback(mMediaRouterCallback);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final class MyMediaRouterCallback extends MediaRouter.Callback {

        private CastDevice mSelectedDevice;
        private GoogleApiClient mApiClient;
        private Cast.Listener mCastClientListener;

        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
            mSelectedDevice = CastDevice.getFromBundle(info.getExtras());
            String routeId = info.getId();

            mCastClientListener = new Cast.Listener() {
                @Override
                public void onApplicationStatusChanged() {
                    if (mApiClient != null) {
                         Log.d(TAG, "onApplicationStatusChanged: "
                                + Cast.CastApi.getApplicationStatus(mApiClient));
                    }
                }

                @Override
                public void onVolumeChanged() {
                    if (mApiClient != null) {
                         Log.d(TAG, "onVolumeChanged: " + Cast.CastApi.getVolume(mApiClient));
                    }
                }

                @Override
                public void onApplicationDisconnected(int errorCode) {
                    // fixme teardown();
                }
            };


            Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
                    .builder(mSelectedDevice, mCastClientListener);

            mApiClient = new GoogleApiClient.Builder(RPSActivity.this)
                    .addApi(Cast.API, apiOptionsBuilder.build())
                    .addConnectionCallbacks(RPSActivity.this)
                    .addOnConnectionFailedListener(RPSActivity.this)
                    .build();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
            // fixme what is this? teardown();
            mSelectedDevice = null;
        }
    }

    private boolean mWaitingForReconnect = false;
    @Override
    public void onConnected(Bundle connectionHint) {
        if (mWaitingForReconnect) {
            mWaitingForReconnect = false;
            // fixme reconnectChannels();
        } else {
            try {
                Cast.CastApi.launchApplication(mApiClient, "21E58E1F", false)
                        .setResultCallback(
                                new ResultCallback<Cast.ApplicationConnectionResult>() {
                                    @Override
                                    public void onResult(Cast.ApplicationConnectionResult result) {
                                        Status status = result.getStatus();
                                        if (status.isSuccess()) {
                                            ApplicationMetadata applicationMetadata =
                                                    result.getApplicationMetadata();
                                            String sessionId = result.getSessionId();
                                            String applicationStatus = result.getApplicationStatus();
                                            boolean wasLaunched = result.getWasLaunched();

                                        } else {
                                            // fixme teardown();
                                        }
                                    }
                                });

            } catch (Exception e) {
                 Log.e(TAG, "Failed to launch application", e);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mWaitingForReconnect = true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // fixme teardown();
    }

    public String getNamespace() {
        return "com.adventurpriseme.gamescast";
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace,
                                  String message) {
        Log.d(TAG, "onMessageReceived: " + message);
    }
}