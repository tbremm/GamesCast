package com.adventurpriseme.gamescast;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adventurpriseme.gamescast.dummy.DummyContent;


/**
 * An activity representing a single Game detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GameListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link GameDetailTitleFragment}.
 */
public class GameDetailActivity extends Activity {


    public static final String ARG_ITEM_ID = "item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(this.ARG_ITEM_ID,
                    getIntent().getStringExtra(this.ARG_ITEM_ID));

            //  todo - could use a more graceful default string
            String title_string = getIntent().getStringExtra("Title");//arguments.getString("Title", "foo");

            // fixme - hacky temporary to get the ball rollin'
            // really should use fragments for better portability
            TextView title = (TextView) findViewById(R.id.game_title);
            title.setText(title_string);

            Button button = (Button) findViewById(R.id.join_game_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GameDetailActivity.this, RPSActivity.class);
                    startActivity(intent);
                }
            });




            //GameDetailTitleFragment fragment_title = new GameDetailTitleFragment();
            //GameDetailTitleFragment fragment_title2 = new GameDetailTitleFragment();
            //GameDetailJoinListFragment fragment_join_list = new GameDetailJoinListFragment();

            //fragment_title.setArguments(arguments);
            //fragment_title2.setArguments(arguments);
            //fragment_join_list.setArguments(arguments);

            //FragmentTransaction transaction = getFragmentManager().beginTransaction();

            //transaction.add(R.id.game_detail_container_title, fragment_title, "first");
            //transaction.add(R.id.game_detail_container_title, fragment_title2, "second");

            //transaction.add(R.id.game_detail_container, fragment_join_list);

            //transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, GameListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
