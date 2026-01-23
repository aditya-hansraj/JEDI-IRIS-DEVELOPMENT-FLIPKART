package com.flipfit.bean;

public class Customer extends User {
    private String customerPhone;
    private String cardDetails;

    public Customer() {
        setRole("Customer");
    }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCardDetails() { return cardDetails; }
    public void setCardDetails(String cardDetails) { this.cardDetails = cardDetails; }
}