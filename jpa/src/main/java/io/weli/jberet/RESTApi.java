package io.weli.jberet;

import jakarta.ws.rs.Path;

@Path("/batch")
public class RESTApi {
    @Path("/start")
    public void start() {
        System.out.println("batch start");
    }

}
