package com.adventurpriseme.gamescast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
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
        // FIXME: Go into chromecast activity
        setContentView(R.layout.activity_game1);
    }

    private void openSettings() {
    }

    public void onGamesMenuSelected (View view) {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity (intent);
    }

    public void on_game_1(View view) {
        setContentView(R.layout.activity_game1);
    }

    public void on_game_2(View view) {
    }

    public void on_game_3(View view) {
    }
}
