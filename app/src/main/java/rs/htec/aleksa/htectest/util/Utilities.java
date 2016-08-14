package rs.htec.aleksa.htectest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by aleksa on 8/14/16.
 *
 * Contains various utilities (such as network status check)
 */

public class Utilities {

    /**
     * Checks whether the device has a network connection
     * @param context Used to get system service
     * @return True if there is a network connection, false otherwise
     */
    public static boolean hasNetworkConnection(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
