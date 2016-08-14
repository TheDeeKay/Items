package rs.htec.aleksa.htectest.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.network.API;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rs.htec.aleksa.htectest.util.Utilities;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aleksa on 8/14/16.
 *
 * A wrapper for fetching data and storing it into Realm
 */

public class FetchData {

    private static final String TAG = "FetchData";

    // Used to avoid overlapping error Toasts
    private static Toast sToast = null;

    /**
     * Fetches the network data on an IO thread and stores it into Realm on an IO thread
     * @param context The context used to open a Realm instance. Best be an applicationContext
     */
    public static void fetchData(Context context){

        API.getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        listItems -> storeData(listItems, context),
                        e -> handleError(e, context),
                        () -> Log.d(TAG, "Successfuly finished fetching data"));
    }

    /**
     * Stores the data in Realm
     * @param items The list of ListItems to store into Realm
     * @param context The context used to open a Realm instance. Best be an applicationContext
     */
    public static void storeData(ArrayList<ListItem> items, Context context){
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder(context).build());
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(items);

        realm.commitTransaction();
        realm.close();

        Log.d(TAG, "Inserted or updated " + items.size() + " items");
    }

    /**
     * Handles an error received during the fetch
     * @param e The throwable received as an error
     * @param context The context in which the error Toast can be shown
     */
    private static void handleError(Throwable e, Context context){

        // If there's no network, inform the user
        Observable.just(Utilities.hasNetworkConnection(context))
                .filter(aBoolean -> !aBoolean) // Filter to emit only if the method returns false
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> toastNoNetwork(context));

        Log.d(TAG, "An error occured during fetch", e);
    }

    /**
     * Shows a Toast informing the user there is no network
     * The toasts are non-overlapping (meaning they are only shown if no such toast is already showing)
     *
     * @param context The context where the Toast will be shown
     */
    private static void toastNoNetwork(Context context){
        // Check if a toast is already showing
        if (sToast == null || sToast.getView().getVisibility() == View.GONE) {
            String message = context.getString(R.string.no_network_error);

            sToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            sToast.show();
        }
    }
}
