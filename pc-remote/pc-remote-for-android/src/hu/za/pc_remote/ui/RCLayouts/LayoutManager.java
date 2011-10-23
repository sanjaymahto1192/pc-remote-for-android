package hu.za.pc_remote.ui.RCLayouts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import hu.za.pc_remote.RCLayoutsManagement.LayoutListItem;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/23/11
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LayoutManager extends Activity {

    ArrayAdapter<LayoutListItem> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}