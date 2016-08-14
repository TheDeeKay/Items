package rs.htec.aleksa.htectest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.pojo.ListItem;

/**
 * Created by aleksa on 8/13/16.
 */

public class ItemListAdapter extends BaseAdapter {

    private List<ListItem> listItems;
    private LayoutInflater inflater;

    public ItemListAdapter(Context context, List<ListItem> listItems){
        this.listItems = listItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listItems != null) {
            return listItems.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (listItems != null) {
            return listItems.get(position);
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
        holder.title.setText(listItems.get(position).getTitle());
        holder.description.setText(listItems.get(position).getDescription());
        // Load image from URL, center crop it and cache it to the disk
        Glide.with(parent.getContext())
                .load(listItems.get(position).getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
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
