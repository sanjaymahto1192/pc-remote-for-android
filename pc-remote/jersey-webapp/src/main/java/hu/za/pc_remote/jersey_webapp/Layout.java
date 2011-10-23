package hu.za.pc_remote.jersey_webapp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import static hu.za.pc_remote.common.LayoutJSONConstants.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/17/11
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Layout {
    private Integer id;
    private String name;
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject result = new JSONObject();
        result.put(ID, id);
        result.put(NAME, name);
        result.put(TEXT, text);
        return result;
    }

    public static Layout getLayoutFromJSON(JSONObject jsonObject) throws JSONException {
        Layout result = new Layout();
        Object o = jsonObject.get(ID);
        result.setId(o != JSONObject.NULL ? new Integer(o.toString()) : null);
        o = jsonObject.get(NAME);
        result.setName(o != JSONObject.NULL ? o.toString() : null);
        o = jsonObject.get(TEXT);
        result.setText(o != JSONObject.NULL ? o.toString() : null);
        return result;
    }
}
