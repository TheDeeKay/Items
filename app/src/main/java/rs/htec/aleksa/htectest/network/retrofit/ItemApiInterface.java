package rs.htec.aleksa.htectest.network.retrofit;

import java.util.ArrayList;

import retrofit2.http.GET;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rx.Observable;

/**
 * Created by aleksa on 8/13/16.
 *
 * Serves as a Retrofit interface for fetching items
 */

public interface ItemApiInterface {

    // Fetches a list of all the ListItems
    @GET("items.json")
    Observable<ArrayList<ListItem>> listAllItems();
}
