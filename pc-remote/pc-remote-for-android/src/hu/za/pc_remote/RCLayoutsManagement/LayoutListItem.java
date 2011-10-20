package hu.za.pc_remote.RCLayoutsManagement;

import hu.za.pc_remote.common.ListItemJSONConstants;
import org.json.JSONException;
import org.json.JSONObject;

import static hu.za.pc_remote.common.ListItemJSONConstants.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/18/11
 * Time: 9:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class LayoutListItem {
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static LayoutListItem parse(JSONObject o) throws JSONException {
        LayoutListItem result = new LayoutListItem();
        result.id = o.getInt(ID);
        result.name = o.getString(NAME);
        return result;
    }
}
