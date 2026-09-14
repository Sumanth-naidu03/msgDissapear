package com.msgDissapear.service;

import com.msgDissapear.dto.AddContactRequest;
import com.msgDissapear.dto.AuthResponse;
import com.msgDissapear.entity.Contact;
import com.msgDissapear.entity.Message;
import com.msgDissapear.repository.ContactRepository;
import com.msgDissapear.repository.MessageRepository;
import com.msgDissapear.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public AuthResponse addContact(AddContactRequest request) {
        String owner = request.getOwner().trim();
        String contactUsername = request.getContactUsername().trim();

        var ownerUser = userRepository.findByUsername(owner);
        if (ownerUser.isEmpty()) {
            return new AuthResponse(null, "Owner account not found", false);
        }
        var targetUser = userRepository.findByUsername(contactUsername);
        if (targetUser.isEmpty()) {
            return new AuthResponse(null, "No user with that username", false);
        }

        String canonicalOwner = ownerUser.get().getUsername();
        String canonicalContact = targetUser.get().getUsername();

        if (canonicalOwner.equals(canonicalContact)) {
            return new AuthResponse(null, "You cannot add yourself", false);
        }
        if (contactRepository.existsByOwnerAndContactUsername(canonicalOwner, canonicalContact)) {
            return new AuthResponse(canonicalContact, "Already in your contacts", false);
        }

        Contact contact = new Contact();
        contact.setOwner(canonicalOwner);
        contact.setContactUsername(canonicalContact);
        contactRepository.save(contact);
        return new AuthResponse(canonicalContact, "Contact added", true);
    }

    @Transactional(readOnly = true)
    public List<String> getContacts(String owner) {
        return contactRepository.findByOwnerOrderByContactUsernameAsc(owner)
                .stream()
                .map(Contact::getContactUsername)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllConversationPartners(String username) {
        // Get explicit contacts
        Set<String> partners = new HashSet<>(getContacts(username));
        
        // Get anyone who has sent messages to this user
        List<Message> receivedMessages = messageRepository.findByRecipientAndExpiresAtAfterOrderByCreatedAtDesc(username, Instant.now());
        for (Message msg : receivedMessages) {
            partners.add(msg.getSender());
        }
        
        // Get anyone this user has sent messages to
        List<Message> sentMessages = messageRepository.findBySenderAndExpiresAtAfterOrderByCreatedAtDesc(username, Instant.now());
        for (Message msg : sentMessages) {
            partners.add(msg.getRecipient());
        }
        
        return partners.stream()
                .filter(p -> !p.equals(username))  // exclude self
                .sorted()
                .collect(Collectors.toList());
    }
}
