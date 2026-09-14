package com.msgDissapear.service;

import com.msgDissapear.dto.MessageResponse;
import com.msgDissapear.dto.SendMessageRequest;
import com.msgDissapear.entity.Message;
import com.msgDissapear.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageResponse send(SendMessageRequest request) {
        Instant expiresAt = Instant.now().plusSeconds(request.getTtlSeconds());

        Message message = Message.builder()
                .sender(request.getSender())
                .recipient(request.getRecipient())
                .content(request.getContent())
                .expiresAt(expiresAt)
                .read(false)
                .build();

        Message saved = messageRepository.save(message);
        log.info("Message {} created; expires at {}", saved.getId(), expiresAt);
        return MessageResponse.from(saved);
    }

    @Transactional
    public List<MessageResponse> getMessagesForRecipient(String recipient) {
        List<Message> messages = messageRepository
                .findByRecipientAndExpiresAtAfterOrderByCreatedAtDesc(recipient, Instant.now());

        messages.stream()
                .filter(m -> !m.isRead())
                .forEach(m -> {
                    m.setRead(true);
                    messageRepository.save(m);
                });

        return messages.stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMessagesBySender(String sender) {
        return messageRepository
                .findBySenderAndExpiresAtAfterOrderByCreatedAtDesc(sender, Instant.now())
                .stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getConversation(String userA, String userB) {
        return messageRepository
                .findConversation(userA, userB, Instant.now())
                .stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MessageResponse getById(Long id) {
        return messageRepository.findById(id)
                .filter(m -> m.getExpiresAt().isAfter(Instant.now()))
                .map(MessageResponse::from)
                .orElse(null);
    }

    @Transactional
    public boolean deleteMessage(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            log.info("Message {} manually deleted", id);
            return true;
        }
        return false;
    }

    @Transactional
    public int purgeExpired() {
        int deleted = messageRepository.deleteAllExpired(Instant.now());
        if (deleted > 0) {
            log.info("Purged {} expired message(s)", deleted);
        }
        return deleted;
    }
}
