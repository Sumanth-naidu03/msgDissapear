package com.msgDissapear.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(
        name = "contacts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"owner", "contact_username"})
)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String owner;

    @NotBlank
    @Column(name = "contact_username", nullable = false)
    private String contactUsername;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Contact() {}

    @PrePersist
    private void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public Long getId() { return id; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getContactUsername() { return contactUsername; }
    public void setContactUsername(String contactUsername) { this.contactUsername = contactUsername; }

    public Instant getCreatedAt() { return createdAt; }
}
