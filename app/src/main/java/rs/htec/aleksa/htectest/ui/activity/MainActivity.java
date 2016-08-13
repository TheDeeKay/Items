package rs.htec.aleksa.htectest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.api.API;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // TODO: All this should get extracted once the database is in
        API.getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ListItem>>() {

                    List<ListItem> items = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mAdapter = new ItemListAdapter(MainActivity.this, items);
                        mListView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Failed to fetch data", e);
                        // TODO: handle this error when this call gets extracted
                    }

                    @Override
                    public void onNext(ArrayList<ListItem> listItems) {
                        items.addAll(listItems);
                    }
                });
    }
}