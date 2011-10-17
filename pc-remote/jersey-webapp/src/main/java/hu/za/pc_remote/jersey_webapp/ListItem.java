package hu.za.pc_remote.jersey_webapp;

import hu.za.pc_remote.common.ListItemJSONConstants;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/17/11
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListItem {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject result = new JSONObject();
        result.put(ListItemJSONConstants.ID, id);
        result.put(ListItemJSONConstants.NAME, name);
        return result;
    }
}
