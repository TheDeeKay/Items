package rs.htec.aleksa.htectest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.network.API;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rs.htec.aleksa.htectest.ui.adapter.ItemListAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        // TODO: All this should get extracted once the database is in
        API.getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ListItem>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Failed to fetch data", e);
                        // TODO: handle this error when this call gets extracted
                    }

                    @Override
                    public void onNext(ArrayList<ListItem> listItems) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();

                        realm.copyToRealmOrUpdate(listItems);

                        realm.commitTransaction();
                        realm.close();
                    }
                });

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

        mListItems.removeChangeListener(mListItemChangeListener);
        mRealm.close();
    }
}