package rs.htec.aleksa.htectest.network;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.htec.aleksa.htectest.network.retrofit.ItemApiInterface;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rx.Observable;

/**
 * Created by aleksa on 8/13/16.
 *
 * A wrapper for Retrofit calls, to avoid all the boilerplate with retrofit instances and such
 */

public class API {

    private static GsonConverterFactory sGsonFactory = GsonConverterFactory.create();
    private static Retrofit sRetrofit =
            new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/danieloskarsson/mobile-coding-exercise/master/")
                    .addConverterFactory(sGsonFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

    private static ItemApiInterface sItemInterface = sRetrofit.create(ItemApiInterface.class);

    public static Observable<ArrayList<ListItem>> getAllItems(){
        return sItemInterface.listAllItems();
    }
}
