package com.msgDissapear.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddContactRequest {

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotBlank(message = "Contact username is required")
    @Size(min = 2, max = 30, message = "Username must be 2–30 characters")
    private String contactUsername;

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getContactUsername() { return contactUsername; }
    public void setContactUsername(String contactUsername) { this.contactUsername = contactUsername; }
}
