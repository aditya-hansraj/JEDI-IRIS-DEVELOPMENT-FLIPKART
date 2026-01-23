package com.flipfit.resources;

import com.flipfit.core.Center;
import com.flipfit.core.Slot;
import com.flipfit.service.CenterService;
import com.flipfit.service.SlotService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Path("/gymowner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymOwnerResource {

    private final CenterService centerService;
    private final SlotService slotService;

    public GymOwnerResource(CenterService centerService, SlotService slotService) {
        this.centerService = centerService;
        this.slotService = slotService;
    }

    // DTO for adding a center
    public static class AddCenterRequest {
        public String name;
        public String address;
        public String gymOwnerId; // Assuming gymOwnerId is provided by the authenticated owner
    }

    @POST
    @Path("/centers")
    public Response addCenter(AddCenterRequest request) {
        if (request.name == null || request.address == null || request.gymOwnerId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Name, address, and gymOwnerId are required.").build();
        }
        Optional<Center> center = centerService.createCenter(request.name, request.address, request.gymOwnerId);
        if (center.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(center.get()).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add center.").build();
    }

    @DELETE
    @Path("/centers/{id}")
    public Response deleteCenter(@PathParam("id") String centerId) {
        // In a real app, verify that the gymOwnerId matches the authenticated owner
        Optional<Center> center = centerService.getCenterById(centerId);
        if (center.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Center not found.").build();
        }
        centerService.deleteCenter(centerId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{gymOwnerId}/centers")
    public List<Center> listCenters(@PathParam("gymOwnerId") String gymOwnerId) {
        return centerService.getCentersByGymOwnerId(gymOwnerId);
    }

    // DTO for adding a slot
    public static class AddSlotRequest {
        public String centerId;
        public String startTime; // e.g., "2026-01-23T10:00:00"
        public String endTime;   // e.g., "2026-01-23T11:00:00"
        public int capacity;
    }

    @POST
    @Path("/slots")
    public Response addSlot(AddSlotRequest request) {
        if (request.centerId == null || request.startTime == null || request.endTime == null || request.capacity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Center ID, start time, end time, and capacity are required.").build();
        }
        try {
            LocalDateTime startTime = LocalDateTime.parse(request.startTime);
            LocalDateTime endTime = LocalDateTime.parse(request.endTime);

            Optional<Slot> slot = slotService.createSlot(request.centerId, startTime, endTime, request.capacity);
            if (slot.isPresent()) {
                return Response.status(Response.Status.CREATED).entity(slot.get()).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add slot.").build();
        } catch (java.time.format.DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date/time format. Use YYYY-MM-DDTHH:MM:SS.").build();
        }
    }

    @DELETE
    @Path("/slots/{id}")
    public Response deleteSlot(@PathParam("id") String slotId) {
        // In a real app, verify ownership of the slot
        Optional<Slot> slot = slotService.getSlotById(slotId);
        if (slot.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Slot not found.").build();
        }
        slotService.deleteSlot(slotId);
        return Response.noContent().build();
    }
}
