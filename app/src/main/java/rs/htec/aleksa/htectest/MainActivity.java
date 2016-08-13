package rs.htec.aleksa.htectest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_list_view)
    ListView mListView;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<String>(
                this, R.layout.main_list_view_item, R.id.main_item_description, DummyData.DUMMY_DATA);

        mListView.setAdapter(mAdapter);
    }
}