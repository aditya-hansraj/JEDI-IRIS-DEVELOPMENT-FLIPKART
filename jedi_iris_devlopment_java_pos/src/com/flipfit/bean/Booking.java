package com.flipfit.bean;

public class Booking {
    private String bookingId;
    private String userId;
    private String slotId;
    private String status; // "Confirmed", "Cancelled"

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}