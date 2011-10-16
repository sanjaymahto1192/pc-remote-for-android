package hu.za.pc_remote.jersey_webapp;

import javax.ws.rs.*;
import java.util.List;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt() {
        List<String> names = DAO.getLayouts();
        StringBuilder result = new StringBuilder();
        result.append("Layouts in the DB:\n");
        for(String s : names){
            result.append(s).append("\n");
        }
        return result.toString();
    }
}
