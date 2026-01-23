package com.flipfit.resources;

import com.flipfit.core.Center;
import com.flipfit.core.GymOwner;
import com.flipfit.core.Status;
import com.flipfit.service.CenterService;
import com.flipfit.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    private final CenterService centerService;
    private final UserService userService;

    public AdminResource(CenterService centerService, UserService userService) {
        this.centerService = centerService;
        this.userService = userService;
    }

    @POST
    @Path("/centers/{id}/approve")
    public Response approveCenter(@PathParam("id") String centerId) {
        Optional<Center> center = centerService.approveCenter(centerId);
        if (center.isPresent()) {
            return Response.ok(center.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Center not found or approval failed.").build();
    }

    @POST
    @Path("/centers/{id}/reject")
    public Response rejectCenter(@PathParam("id") String centerId) {
        Optional<Center> center = centerService.rejectCenter(centerId);
        if (center.isPresent()) {
            return Response.ok(center.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Center not found or rejection failed.").build();
    }

    @POST
    @Path("/gymowners/{id}/approve")
    public Response approveGymOwner(@PathParam("id") String gymOwnerId) {
        Optional<com.flipfit.core.User> user = userService.findUserById(gymOwnerId);
        if (user.isPresent() && user.get() instanceof GymOwner) {
            GymOwner gymOwner = (GymOwner) user.get();
            gymOwner.setApprovalStatus(Status.APPROVED);
            userService.updateUser(gymOwner);
            return Response.ok(gymOwner).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Gym Owner not found or approval failed.").build();
    }

    @POST
    @Path("/gymowners/{id}/reject")
    public Response rejectGymOwner(@PathParam("id") String gymOwnerId) {
        Optional<com.flipfit.core.User> user = userService.findUserById(gymOwnerId);
        if (user.isPresent() && user.get() instanceof GymOwner) {
            GymOwner gymOwner = (GymOwner) user.get();
            gymOwner.setApprovalStatus(Status.REJECTED);
            userService.updateUser(gymOwner);
            return Response.ok(gymOwner).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Gym Owner not found or rejection failed.").build();
    }
}
