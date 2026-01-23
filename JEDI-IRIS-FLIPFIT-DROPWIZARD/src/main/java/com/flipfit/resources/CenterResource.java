package com.flipfit.resources;

import com.flipfit.core.Center;
import com.flipfit.service.CenterService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/centers")
@Produces(MediaType.APPLICATION_JSON)
public class CenterResource {

    private final CenterService centerService;

    public CenterResource(CenterService centerService) {
        this.centerService = centerService;
    }

    @GET
    @Path("/{id}")
    public Response getCenter(@PathParam("id") String id) {
        return centerService.getCenterById(id)
                .map(center -> Response.ok(center).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Center not found.").build());
    }

    @GET
    public List<Center> getAllCenters() {
        return centerService.getAllCenters();
    }

    // Note: Add/update/delete operations for centers are handled by GymOwnerResource and AdminResource for approval.
    // This resource primarily provides read access.
}
