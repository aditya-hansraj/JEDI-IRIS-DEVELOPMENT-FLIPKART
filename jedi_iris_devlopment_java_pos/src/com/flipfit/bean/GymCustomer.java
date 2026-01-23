package com.flipfit.bean;

public class GymCustomer extends User {
    private String customerPhone;
    private String cardDetails;

    public GymCustomer() {
        // We set the Role Object here
        setRole(new Role("1", "Customer", "Can book gym slots"));
    }

    // (Keep your existing getters and setters below...)
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCardDetails() { return cardDetails; }
    public void setCardDetails(String cardDetails) { this.cardDetails = cardDetails; }
}