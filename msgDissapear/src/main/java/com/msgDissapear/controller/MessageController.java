package com.msgDissapear.controller;

import com.msgDissapear.dto.MessageResponse;
import com.msgDissapear.dto.SendMessageRequest;
import com.msgDissapear.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> send(@Valid @RequestBody SendMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.send(request));
    }

    @GetMapping("/inbox/{recipient}")
    public ResponseEntity<List<MessageResponse>> getInbox(@PathVariable String recipient) {
        return ResponseEntity.ok(messageService.getMessagesForRecipient(recipient));
    }

    @GetMapping("/sent/{sender}")
    public ResponseEntity<List<MessageResponse>> getSent(@PathVariable String sender) {
        return ResponseEntity.ok(messageService.getMessagesBySender(sender));
    }

    @GetMapping("/conversation/{userA}/{userB}")
    public ResponseEntity<List<MessageResponse>> getConversation(
            @PathVariable String userA,
            @PathVariable String userB) {
        return ResponseEntity.ok(messageService.getConversation(userA, userB));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getById(@PathVariable Long id) {
        MessageResponse response = messageService.getById(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return messageService.deleteMessage(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
