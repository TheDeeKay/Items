package rs.htec.aleksa.htectest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.htec.aleksa.htectest.DummyData;
import rs.htec.aleksa.htectest.ui.adapter.ItemListAdapter;
import rs.htec.aleksa.htectest.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_list_view)
    ListView mListView;

    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new ItemListAdapter(this, DummyData.DUMMY_ITEMS);

        mListView.setAdapter(mAdapter);
    }
}