package com.msgDissapear.controller;

import com.msgDissapear.dto.AddContactRequest;
import com.msgDissapear.dto.AuthResponse;
import com.msgDissapear.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> add(@Valid @RequestBody AddContactRequest request) {
        AuthResponse res = contactService.addContact(request);
        return res.isSuccess()
                ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @GetMapping("/{owner}")
    public ResponseEntity<List<String>> list(@PathVariable String owner) {
        return ResponseEntity.ok(contactService.getContacts(owner));
    }

    @GetMapping("/{owner}/conversations")
    public ResponseEntity<List<String>> getAllConversations(@PathVariable String owner) {
        return ResponseEntity.ok(contactService.getAllConversationPartners(owner));
    }
}
