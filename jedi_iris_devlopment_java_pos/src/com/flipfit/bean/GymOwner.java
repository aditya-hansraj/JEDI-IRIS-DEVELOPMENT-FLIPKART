package com.flipfit.bean;

import java.util.List;

public class GymOwner extends User {
    private String panNumber;
    private String cardDetails;
    private boolean isApproved; // False by default

    public GymOwner() {
        setRole("GymOwner");
        this.isApproved = false;
    }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public String getCardDetails() { return cardDetails; }
    public void setCardDetails(String cardDetails) { this.cardDetails = cardDetails; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}