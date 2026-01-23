package com.flipfit.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class RootResource {
    @GET
    public String sayHello() {
        return "Hello from FlipFit!";
    }
}
