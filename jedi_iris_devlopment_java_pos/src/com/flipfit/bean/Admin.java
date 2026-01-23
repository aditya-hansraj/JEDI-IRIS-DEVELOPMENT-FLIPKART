package com.flipfit.bean;

public class Admin extends User {
    private String adminId;
    private String accountNumber;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Admin() {
        setRole("Admin");
    }

}