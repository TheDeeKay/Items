package rs.htec.aleksa.htectest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.pojo.ListItem;

/**
 * Created by aleksa on 8/13/16.
 */

public class ItemListAdapter extends RealmBaseAdapter<ListItem> {
    private LayoutInflater inflater;

    public ItemListAdapter(Context context, OrderedRealmCollection<ListItem> data) {
        super(context, data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (adapterData != null) {
            return adapterData.size();
        }
        return 0;
    }

    @Override
    public ListItem getItem(int position) {
        if (adapterData != null) {
            return adapterData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // If there's nothing to recycle, inflate a new View and create a ViewHolder and attach it
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_list_view_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        // Get a ViewHolder
        ViewHolder holder = (ViewHolder) convertView.getTag();

        // Properly set the contents of the view
        holder.title.setText(adapterData.get(position).getTitle());
        holder.description.setText(adapterData.get(position).getDescription());
        // Load image from URL, center crop it and cache it to the disk
        Glide.with(parent.getContext())
                .load(adapterData.get(position).getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache all since master and detail have different sizes
                .into(holder.image);

        return convertView;
    }

    /**
     * Serves to implement a ViewHolder pattern
     */
    static class ViewHolder {
        @BindView(R.id.main_item_image)
        ImageView image;

        @BindView(R.id.main_item_title)
        TextView title;

        @BindView(R.id.main_item_description)
        TextView description;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
