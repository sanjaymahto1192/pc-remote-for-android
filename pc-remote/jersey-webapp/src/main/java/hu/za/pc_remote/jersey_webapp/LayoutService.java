package hu.za.pc_remote.jersey_webapp;

import hu.za.pc_remote.common.QueryParamConstants;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import java.util.List;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/layouts")
public class LayoutService {

    /**
     * Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     *
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET
    @Produces("application/json")
    @Path("/list")
    public JSONArray getIt() throws JSONException {
        JSONArray result = new JSONArray();
        List<LayoutListItem> items = DAO.getLayouts();
        for (LayoutListItem item : items) {
            result.put(item.toJSON());
        }
        return result;
    }

    @GET
    @Produces("application/json")
    public JSONObject getLayout(@QueryParam(QueryParamConstants.ID) int id) throws JSONException {
        Layout result = DAO.getLayout(id);
        return result.toJSON();
    }

    @POST
    @Consumes("application/json")
    public void updateLayout(JSONObject body) throws JSONException {
        Layout layout = Layout.getLayoutFromJSON(body);
        DAO.updateLayout(layout);
    }

    @PUT
    @Consumes("application/json")
    public void putLayout(JSONObject body) throws JSONException {
        Layout layout = Layout.getLayoutFromJSON(body);
        DAO.insertLayout(layout);
    }

    @DELETE
    public void deleteLayout(@QueryParam(QueryParamConstants.ID) int id) throws JSONException {
        DAO.deleteLayout(id);
    }
}
