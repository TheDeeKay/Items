package rs.htec.aleksa.htectest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import rs.htec.aleksa.htectest.network.API;
import rs.htec.aleksa.htectest.pojo.ListItem;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by aleksa on 8/13/16.
 *
 * Instrumentation test for checking whether our Retrofit API calls are working
 */

@RunWith(AndroidJUnit4.class)
public class RetrofitTest {

    @Test
    public void testApiCall(){

        API.getAllItems()
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<ListItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        throw new AssertionError();
                    }

                    @Override
            public void onNext(ArrayList<ListItem> listItems) {
                assert listItems != null && !listItems.isEmpty();
            }
        });
    }
}
