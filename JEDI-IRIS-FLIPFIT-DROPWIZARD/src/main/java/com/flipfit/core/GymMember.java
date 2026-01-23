package com.flipfit.core;

import java.util.Objects;

public class GymMember extends User {
    private String creditCard; // Placeholder, in a real app this would be more secure (e.g., tokenized)

    public GymMember() {
        super();
        this.setRole(Role.GYM_MEMBER);
    }

    public GymMember(String id, String name, String email, String password, String creditCard) {
        super(id, name, email, password, Role.GYM_MEMBER);
        this.creditCard = creditCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Compare User fields
        GymMember gymMember = (GymMember) o;
        return Objects.equals(creditCard, gymMember.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creditCard);
    }
}
