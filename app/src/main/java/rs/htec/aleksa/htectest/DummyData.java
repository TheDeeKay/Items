package rs.htec.aleksa.htectest;

import java.util.ArrayList;

import rs.htec.aleksa.htectest.pojo.ListItem;

/**
 * Created by aleksa on 8/13/16.
 *
 * Represents some dummy data to be displayed in the main list view
 * TODO: to be replaced with parsed JSONs when the GSON is implemented
 */

public class DummyData {

    public static final String DUMMY_DESCRIPTIONS[] = new String[]{

            "explanation cable control electricians thousand capitals classification conn preserver " +
                    "thirds quartermaster puffs countermeasure armful need discretion similarity " +
                    "fists rust mouths attempts giants dives parameters admirals destroyers " +
                    "alternatives exit paints cab thirteen bolts breaches allowances certificate " +
                    "trails linkage launch contacts moss salts elbows tomorrows",

            "alarm hull wishes flesh surrender others street cliffs chain milestone audit agreement deployment",

            "sterilizer span ticks continuity hubs procurement vision eggs backups cries gap iron " +
                    "conferences torpedo government catchers restaurant destroyers attribute " +
                    "counsel echo overcurrent classes trip environments forecastle giants " +
                    "conspiracies suppression things rope plans bow blots rescuers incline",

            "restraints sod detention alternation pits instance spindles screwdrivers smile worksheet " +
                    "amusements stub jams breezes bush engine wash official talker shaft states"
    };

    public static final ArrayList<ListItem> DUMMY_ITEMS;

    static {
        DUMMY_ITEMS = new ArrayList<>();
        for (int i = 0; i < DUMMY_DESCRIPTIONS.length; i++){

            ListItem listItem = new ListItem();
            listItem.setTitle("Item title " + (i + 1));
            listItem.setDescription(DUMMY_DESCRIPTIONS[i]);

            DUMMY_ITEMS.add(listItem);
        }
    }

}
