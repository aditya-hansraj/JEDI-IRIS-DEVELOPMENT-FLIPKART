package com.flipfit.resources;

import com.flipfit.core.Booking;
import com.flipfit.service.BookingService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    private final BookingService bookingService;

    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GET
    @Path("/{id}")
    public Response getBooking(@PathParam("id") String id) {
        return bookingService.getBookingById(id)
                .map(booking -> Response.ok(booking).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Booking not found.").build());
    }

    @GET
    @Path("/by-member/{gymMemberId}")
    public List<Booking> getBookingsByGymMember(@PathParam("gymMemberId") String gymMemberId) {
        return bookingService.getBookingsByGymMemberId(gymMemberId);
    }

    @GET
    @Path("/by-slot/{slotId}")
    public List<Booking> getBookingsBySlot(@PathParam("slotId") String slotId) {
        return bookingService.getBookingsBySlotId(slotId);
    }

    @GET
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @DELETE
    @Path("/{id}")
    public Response cancelBooking(@PathParam("id") String id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Booking not found.").build();
        }
        bookingService.deleteBooking(id);
        return Response.noContent().build();
    }

    // Note: Create booking is handled by GymMemberResource as part of the booking flow.
}
