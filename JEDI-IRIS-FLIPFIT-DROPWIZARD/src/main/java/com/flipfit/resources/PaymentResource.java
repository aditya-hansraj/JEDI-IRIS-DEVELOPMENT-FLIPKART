package com.flipfit.resources;

import com.flipfit.core.Payment;
import com.flipfit.service.PaymentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GET
    @Path("/{id}")
    public Response getPayment(@PathParam("id") String id) {
        return paymentService.getPaymentById(id)
                .map(payment -> Response.ok(payment).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Payment not found.").build());
    }

    @GET
    @Path("/by-member/{gymMemberId}")
    public List<Payment> getPaymentsByGymMember(@PathParam("gymMemberId") String gymMemberId) {
        return paymentService.getPaymentsByGymMemberId(gymMemberId);
    }

    @GET
    @Path("/by-slot/{slotId}")
    public List<Payment> getPaymentsBySlot(@PathParam("slotId") String slotId) {
        return paymentService.getPaymentsBySlotId(slotId);
    }

    @GET
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // Note: Processing payment is typically part of a larger workflow (e.g., after booking),
    // and might be initiated from another resource or a dedicated payment gateway webhook.
    // For simplicity, we're not exposing a direct POST /payments endpoint here for creation,
    // as it's assumed to be handled internally by a service method like processPayment().
}
