package com.flipfit.resources;

import com.flipfit.core.Booking;
import com.flipfit.core.Center;
import com.flipfit.core.Slot;
import com.flipfit.service.BookingService;
import com.flipfit.service.CenterService;
import com.flipfit.service.SlotService;
import com.flipfit.service.WaitListService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/gymmember")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymMemberResource {

    private final CenterService centerService;
    private final SlotService slotService;
    private final BookingService bookingService;
    private final WaitListService waitListService;

    public GymMemberResource(CenterService centerService, SlotService slotService, BookingService bookingService, WaitListService waitListService) {
        this.centerService = centerService;
        this.slotService = slotService;
        this.bookingService = bookingService;
        this.waitListService = waitListService;
    }

    @GET
    @Path("/centers")
    public List<Center> listCenters() {
        return centerService.getAllCenters();
    }

    @GET
    @Path("/centers/{centerId}/slots")
    public List<Slot> listAvailableSlots(@PathParam("centerId") String centerId) {
        return slotService.getSlotsByCenterId(centerId).stream()
                .filter(slot -> slot.getRemainingSeats() > 0 && slot.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @POST
    @Path("/{gymMemberId}/book/{slotId}")
    public Response bookSlot(@PathParam("gymMemberId") String gymMemberId, @PathParam("slotId") String slotId) {
        Optional<Booking> booking = bookingService.createBooking(gymMemberId, slotId);
        if (booking.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(booking.get()).build();
        } else {
            // If direct booking fails, offer to add to waitlist
            return Response.status(Response.Status.CONFLICT)
                    .entity("Slot is full. Would you like to join the waitlist?")
                    .build();
        }
    }

    @POST
    @Path("/{gymMemberId}/waitlist/{slotId}")
    public Response joinWaitList(@PathParam("gymMemberId") String gymMemberId, @PathParam("slotId") String slotId) {
        Optional<com.flipfit.core.WaitList> waitListEntry = waitListService.addToWaitList(gymMemberId, slotId);
        if (waitListEntry.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(waitListEntry.get()).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity("Could not add to waitlist. Slot might be available or already on waitlist.").build();
        }
    }

    @GET
    @Path("/{gymMemberId}/schedule")
    public List<Booking> getSchedule(@PathParam("gymMemberId") String gymMemberId) {
        return bookingService.getBookingsByGymMemberId(gymMemberId).stream()
                .filter(booking -> {
                    Optional<Slot> slot = slotService.getSlotById(booking.getSlotId());
                    return slot.isPresent() && slot.get().getEndTime().isAfter(LocalDateTime.now());
                })
                .collect(Collectors.toList());
    }
}
