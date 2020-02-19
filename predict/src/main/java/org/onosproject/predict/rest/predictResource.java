package org.onosproject.predict.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.predict.predictService;
import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Path("predict")
public class predictResource extends AbstractWebResource {
    private predictService cs = get(predictService.class);
    @GET
    @Path("prediction")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prediction(){
        String prediction=String.valueOf(cs.getpredict());
        ObjectNode root = mapper().createObjectNode();

        root.put("Prediction of link11-21", prediction)
                .put("real",cs.getReal());
        return ok(root.toString()).build();
    }

}
