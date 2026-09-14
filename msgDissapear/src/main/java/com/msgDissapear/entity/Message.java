package com.msgDissapear.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String sender;

    @NotBlank
    @Column(nullable = false)
    private String recipient;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull
    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean read;

    public Message() {}

    private Message(Builder builder) {
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.content = builder.content;
        this.expiresAt = builder.expiresAt;
        this.read = builder.read;
    }

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    // --- Builder ---

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String sender;
        private String recipient;
        private String content;
        private Instant expiresAt;
        private boolean read;

        public Builder sender(String sender) { this.sender = sender; return this; }
        public Builder recipient(String recipient) { this.recipient = recipient; return this; }
        public Builder content(String content) { this.content = content; return this; }
        public Builder expiresAt(Instant expiresAt) { this.expiresAt = expiresAt; return this; }
        public Builder read(boolean read) { this.read = read; return this; }

        public Message build() { return new Message(this); }
    }
}
