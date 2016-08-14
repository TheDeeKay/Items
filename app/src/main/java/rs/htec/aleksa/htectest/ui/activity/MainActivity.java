package rs.htec.aleksa.htectest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.data.FetchData;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rs.htec.aleksa.htectest.ui.adapter.ItemListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_list_view)
    ListView mListView;

    private ItemListAdapter mAdapter;

    // Used to notify UI of database changes
    private Realm mRealm;
    private RealmChangeListener<RealmResults<ListItem>> mListItemChangeListener;
    private RealmResults<ListItem> mListItems;

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
        mListItemChangeListener = new RealmChangeListener<RealmResults<ListItem>>() {
            @Override
            public void onChange(RealmResults<ListItem> element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        mListItems.addChangeListener(mListItemChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove Realm listeners and close the Realm
        mListItems.removeChangeListener(mListItemChangeListener);
        mRealm.close();
    }
}