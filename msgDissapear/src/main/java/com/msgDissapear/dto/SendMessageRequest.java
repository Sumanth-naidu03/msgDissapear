package com.msgDissapear.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendMessageRequest {

    @NotBlank(message = "sender must not be blank")
    private String sender;

    @NotBlank(message = "recipient must not be blank")
    private String recipient;

    @NotBlank(message = "content must not be blank")
    private String content;

    @NotNull(message = "ttlSeconds is required")
    @Min(value = 1, message = "ttlSeconds must be at least 1")
    private Long ttlSeconds;

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getTtlSeconds() { return ttlSeconds; }
    public void setTtlSeconds(Long ttlSeconds) { this.ttlSeconds = ttlSeconds; }
}
