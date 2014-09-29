package com.adventurpriseme.gamescast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.cast.CastMediaControlIntent;


public class MainActivity extends Activity {

    // Objects required for casting, must exist for lifetime of app
    public static MediaRouter mMediaRouter;
    public static MediaRouteSelector mMediaRouteSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //      InitCasting ();

        if (getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Initialize objects needed for casting
    private void InitCasting() {
        // Get an instance of the MediaRouter (CC device)
        // We need to hold on to this instance for the lifetime of the sender app
        MainActivity.mMediaRouter = MediaRouter.getInstance(getApplicationContext());

        // This is used to filter discovery for Cast devices that can launch the associated receiver app
        mMediaRouteSelector = new MediaRouteSelector.Builder()
            .addControlCategory(CastMediaControlIntent.categoryForCast("21857AF0"))
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_cast:
                onCast();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ChromeCast button handler
    private void onCast() {
        doCast ();
    }

    // Entry point for ChromeCastActivity
    private void doCast() {
        Intent intent = new Intent(this, ChromeCastActivity.class);
        startActivity (intent);
        //Intent intent = new Intent(this, CastHelloWorld.class);
        //startActivity (intent);
    }

    private void openSettings() {
    }

    public void onGamesMenuSelected (View view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity (intent);
    }

    public void on_game_1(View view) {
    }

    public void on_game_2(View view) {
    }

    public void on_game_3(View view) {
    }
}
