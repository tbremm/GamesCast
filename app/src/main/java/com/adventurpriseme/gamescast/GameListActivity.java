package com.adventurpriseme.gamescast;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.adventurpriseme.gamescast.dummy.DummyContent;


/**
 * An activity representing a list of Games. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GameDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GameListFragment} and the item details
 * (if present) is a {@link GameDetailTitleFragment}.
 * <p>
 * This activity also implements the required
 * {@link GameListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GameListActivity extends Activity
        implements GameListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    private DummyContent.DummyItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        // Show the Up button in the action bar.
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.game_detail_container_title) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((GameListFragment) getFragmentManager()
                    .findFragmentById(R.id.game_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
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
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link GameListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            // fixme two pane now broken
            //arguments.putString(GameDetailTitleFragment.ARG_ITEM_ID, id);
            //GameDetailTitleFragment fragment = new GameDetailTitleFragment();
            //fragment.setArguments(arguments);
            //getFragmentManager().beginTransaction()
            //        .replace(R.id.game_detail_container_title, fragment)
            //        .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, GameDetailActivity.class);
            DummyContent.DummyItem mItem;

            mItem = DummyContent.ITEM_MAP.get(id);
            detailIntent.putExtra("Title", mItem.content);
            startActivity(detailIntent);
        }
    }
}
