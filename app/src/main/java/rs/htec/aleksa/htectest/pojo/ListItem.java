package rs.htec.aleksa.htectest.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksa on 8/13/16.
 *
 * A POJO representing our item from the JSON response
 */

public class ListItem extends RealmObject{

    @SerializedName("image")
    private String imageUrl;
    @SerializedName("title")
    @PrimaryKey
    private String title;
    @SerializedName("description")
    private String description;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
