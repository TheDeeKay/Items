package rs.htec.aleksa.htectest.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rs.htec.aleksa.htectest.R;
import rs.htec.aleksa.htectest.network.API;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rs.htec.aleksa.htectest.util.Utilities;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by aleksa on 8/14/16.
 *
 * A wrapper for fetching data and storing it into Realm
 */

public class FetchData {

    private static final String TAG = "FetchData";

    // Those determine the fetch retry interval in seconds and the maximum number of retries
    private static final int RETRY_INTERVAL = 5;
    private static final int MAX_RETRIES = 10;

    // Used to avoid overlapping error Toasts
    private static Toast sToast = null;

    /**
     * A wrapper for the fetch with no onComplete call
     */
    public static void fetchData(Context context){
        fetchData(context, null);
    }

    /**
     * Fetches the network data on an IO thread and stores it into Realm on an IO thread
     * @param context The context used to open a Realm instance. Best be an applicationContext
     * @param onComplete The action to be performed when this fetch finishes
     */
    public static void fetchData(Context context, Action0 onComplete){

        API.getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retryWhen(getRetryWhen(context))
                .subscribe(
                        listItems -> storeData(listItems, context),
                        e -> {
                            handleError(e);
                            callOnComplete(onComplete);
                        },
                        () -> {
                            Log.d(TAG, "Successfuly finished fetching data");
                            callOnComplete(onComplete);
                        });
    }

    /**
     * A helper method that calls an Action0 if it's not null and executes it on main thread
     * @param onComplete the action to execute, can be null
     */
    private static void callOnComplete(Action0 onComplete){
        if (onComplete != null) {
            Observable.just(null)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> onComplete.call());
        }
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
     */
    private static void handleError(Throwable e){

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

    private static Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> getRetryWhen(Context context){

        return observable -> {

            // Each time there's an error, check if there's network and the first time this happens
            // display the toast error on the main thread
            observable
                    .filter(o -> !Utilities.hasNetworkConnection(context))
                    .take(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> toastNoNetwork(context));

            return observable
                    // If there's no network, retry after RETRY_INTERVAL seconds. Otherwise, go to onError
                    .flatMap(o -> {
                        if (!Utilities.hasNetworkConnection(context)){
                            return Observable.timer(RETRY_INTERVAL, TimeUnit.SECONDS);
                        }
                        return Observable.error(o);
                    })
                    // Retry for a maximum of MAX_RETRIES times
                    .zipWith(Observable.range(1, MAX_RETRIES), (aLong, integer) -> integer);
        };
    }
}
