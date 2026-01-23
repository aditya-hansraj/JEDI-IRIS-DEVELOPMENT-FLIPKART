package com.flipfit.resources;

import com.flipfit.core.Slot;
import com.flipfit.service.SlotService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/slots")
@Produces(MediaType.APPLICATION_JSON)
public class SlotResource {

    private final SlotService slotService;

    public SlotResource(SlotService slotService) {
        this.slotService = slotService;
    }

    @GET
    @Path("/{id}")
    public Response getSlot(@PathParam("id") String id) {
        return slotService.getSlotById(id)
                .map(slot -> Response.ok(slot).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Slot not found.").build());
    }

    @GET
    @Path("/by-center/{centerId}")
    public List<Slot> getSlotsByCenter(@PathParam("centerId") String centerId) {
        return slotService.getSlotsByCenterId(centerId);
    }

    @GET
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    // Note: Add/delete operations for slots are handled by GymOwnerResource.
    // This resource primarily provides read access and possibly update (e.g. changing capacity, but this would
    // require careful consideration with existing bookings).
}
