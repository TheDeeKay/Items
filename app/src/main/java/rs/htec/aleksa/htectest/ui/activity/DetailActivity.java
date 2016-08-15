package rs.htec.aleksa.htectest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.constant.Constants;

/**
 * Created by aleksa on 8/14/16.
 *
 * An activity that displays a single ListItem
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_image)
    ImageView mImage;

    @BindView(R.id.detail_title)
    TextView mTitle;

    @BindView(R.id.detail_description)
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Display the back button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup the UI
        initializeUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // Finish with the same exit transition when home is pressed
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the UI using data passed with the intent
     */
    private void initializeUi(){
        Intent intent = getIntent();

        // Instantiate the views
        mTitle.setText(intent.getStringExtra(Constants.ITEM_TITLE_KEY));
        mDescription.setText(intent.getStringExtra(Constants.ITEM_DESCRIPTION_KEY));
        Glide.with(this)
                .load(intent.getStringExtra(Constants.ITEM_IMAGE_URL_KEY))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImage);
    }
}
