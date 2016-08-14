package rs.htec.aleksa.htectest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.constant.Constants;
import rs.htec.aleksa.htectest.data.FetchData;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rs.htec.aleksa.htectest.ui.adapter.ItemListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_list_view)
    ListView mListView;

    @BindView(R.id.main_swipe_layout)
    SwipeRefreshLayout swipeContainer;

    private ItemListAdapter mAdapter;

    // Used to notify UI of database changes
    private Realm mRealm;
    private RealmChangeListener<RealmResults<ListItem>> mListItemChangeListener;
    private RealmResults<ListItem> mListItems;

    /**
     * Launches the detail activity for the selected item
     * The launching intent contains title, description and image url for the given item
     */
    @OnItemClick(R.id.main_list_view)
    void launchDetails(int position) {
        ListItem item = mAdapter.getItem(position);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.ITEM_TITLE_KEY, item.getTitle());
        intent.putExtra(Constants.ITEM_DESCRIPTION_KEY, item.getDescription());
        intent.putExtra(Constants.ITEM_IMAGE_URL_KEY, item.getImageUrl());

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRealm = Realm.getDefaultInstance();

        FetchData.fetchData(getApplicationContext());

        // Get all the ListItems, create an adapter for them and set them to the listview
        mListItems = mRealm.where(ListItem.class).findAll();
        mAdapter = new ItemListAdapter(this, mListItems);
        mListView.setAdapter(mAdapter);

        // Listen for changes in ListItem Realm and notify the adapter of changes
        mListItemChangeListener = element -> mAdapter.notifyDataSetChanged();
        mListItems.addChangeListener(mListItemChangeListener);

        // Attach a listener for the pulldown refresh (to just fetch data)
        swipeContainer.setOnRefreshListener(
                () -> FetchData.fetchData(
                        getApplicationContext(),
                        () -> swipeContainer.setRefreshing(false))
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove Realm listeners and close the Realm
        mListItems.removeChangeListener(mListItemChangeListener);
        mRealm.close();
    }
}