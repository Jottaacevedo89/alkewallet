package com.javiera.alke.dto;

public class ContactoDTO {
    private String userId;
    private String contactUserId;
    private String nameContactUserId;
    private String email;

    // Getters y Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(String contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getNameContactUserId() {
        return nameContactUserId;
    }

    public void setNameContactUserId(String nameContactUserId) {
        this.nameContactUserId = nameContactUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
