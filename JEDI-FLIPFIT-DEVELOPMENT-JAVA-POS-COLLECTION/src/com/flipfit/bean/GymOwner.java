package com.flipfit.bean;

public class GymOwner extends User {
    private String panNumber;
    private String cardDetails;
    private boolean isApproved;

    public GymOwner() {
        // Set Role Object
        setRole(new Role("2", "GymOwner", "Can add centers and slots"));
        this.isApproved = false;
    }

    // (Keep your existing getters and setters...)
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public String getCardDetails() { return cardDetails; }
    public void setCardDetails(String cardDetails) { this.cardDetails = cardDetails; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}