package com.msgDissapear.dto;

import com.msgDissapear.entity.Message;

import java.time.Instant;

public class MessageResponse {

    private Long id;
    private String sender;
    private String recipient;
    private String content;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean read;
    private long ttlRemainingSeconds;

    private MessageResponse() {}

    private MessageResponse(Builder builder) {
        this.id = builder.id;
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.content = builder.content;
        this.createdAt = builder.createdAt;
        this.expiresAt = builder.expiresAt;
        this.read = builder.read;
        this.ttlRemainingSeconds = builder.ttlRemainingSeconds;
    }

    public static MessageResponse from(Message message) {
        long ttl = message.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond();
        return new Builder()
                .id(message.getId())
                .sender(message.getSender())
                .recipient(message.getRecipient())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .expiresAt(message.getExpiresAt())
                .read(message.isRead())
                .ttlRemainingSeconds(ttl)
                .build();
    }

    // --- Getters ---

    public Long getId() { return id; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRead() { return read; }
    public long getTtlRemainingSeconds() { return ttlRemainingSeconds; }

    // --- Builder ---

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String sender;
        private String recipient;
        private String content;
        private Instant createdAt;
        private Instant expiresAt;
        private boolean read;
        private long ttlRemainingSeconds;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder sender(String sender) { this.sender = sender; return this; }
        public Builder recipient(String recipient) { this.recipient = recipient; return this; }
        public Builder content(String content) { this.content = content; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder expiresAt(Instant expiresAt) { this.expiresAt = expiresAt; return this; }
        public Builder read(boolean read) { this.read = read; return this; }
        public Builder ttlRemainingSeconds(long ttl) { this.ttlRemainingSeconds = ttl; return this; }

        public MessageResponse build() { return new MessageResponse(this); }
    }
}
