package com.test.health;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Slf4j
@Component
@Path("/")
public class HealthService {

    private final GeneralHealthService generalHealthService;

    @Autowired
    public HealthService(GeneralHealthService generalHealthService){
        this.generalHealthService = generalHealthService;
    }

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHealth(@QueryParam("general") @DefaultValue("false") boolean general){
        log.info("get Health");
        JsonObject health = new JsonObject();
        boolean globalHealth = true;

        JsonObject checks = new JsonObject();
        health.addProperty("status", true);

        if(general) {
            if(this.generalHealthService.areYouOk()) {
                return Response.status(Status.OK).entity(health.toString()).build();
            } else {
                return Response.status(Status.SERVICE_UNAVAILABLE).entity(health.toString()).build();
            }
        }
        return Response.status(Status.OK).entity(health.toString()).build();

    }


}
