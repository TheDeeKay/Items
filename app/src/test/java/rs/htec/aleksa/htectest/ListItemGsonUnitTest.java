package rs.htec.aleksa.htectest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rs.htec.aleksa.htectest.pojo.ListItem;

/**
 * Created by aleksa on 8/13/16.
 *
 * Tests whether GSON is correctly parsing our response JSON into ListItem objects
 */

public class ListItemGsonUnitTest {

    static String multipleItems = "[{\"image\":\"http://dummyimage.com/715x350/105B19/907ECC\",\"description\":\"sterilizer span ticks continuity hubs procurement vision eggs backups cries gap iron conferences torpedo government catchers restaurant destroyers attribute counsel echo overcurrent classes trip environments forecastle giants conspiracies suppression things rope plans bow blots rescuers incline\",\"title\":\"terminations map autos sons utilizations\"},{\"image\":\"http://dummyimage.com/609x750/0D2637/BCE8DA\",\"description\":\"alarm hull wishes flesh surrender others street cliffs chain milestone audit agreement deployment\",\"title\":\"zone initial\"},{\"image\":\"http://dummyimage.com/291x515/D44707/BB4AD9\",\"description\":\"explanation cable control electricians thousand capitals classification conn preserver thirds quartermaster puffs countermeasure armful need discretion similarity fists rust mouths attempts giants dives parameters admirals destroyers alternatives exit paints cab thirteen bolts breaches allowances certificate trails linkage launch contacts moss salts elbows tomorrows\",\"title\":\"organs ropes\"}]";
    static String singleItem = "{\"image\":\"http://dummyimage.com/715x350/105B19/907ECC\",\"description\":\"sterilizer span ticks continuity hubs procurement vision eggs backups cries gap iron conferences torpedo government catchers restaurant destroyers attribute counsel echo overcurrent classes trip environments forecastle giants conspiracies suppression things rope plans bow blots rescuers incline\",\"title\":\"terminations map autos sons utilizations\"}";

    private Gson gson;

    @Before
    public void setUp(){
        gson = new Gson();
    }

    @Test
    public void testGsonParsingIsCorrect() throws IOException {

        ListItem item = gson.fromJson(singleItem, ListItem.class);
        assert item != null && item.getTitle().equals("terminations map autos sons utilizations");

        Type listType = new TypeToken<List<ListItem>>(){}.getType();
        List<ListItem> items = gson.fromJson(multipleItems, listType);
        assert items.size() == 3 && items.get(0).getTitle().equals("terminations map autos sons utilizations");
    }
}
