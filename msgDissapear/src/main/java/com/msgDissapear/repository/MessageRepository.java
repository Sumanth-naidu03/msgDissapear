package com.msgDissapear.repository;

import com.msgDissapear.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /** All non-expired messages for a recipient, ordered newest first */
    List<Message> findByRecipientAndExpiresAtAfterOrderByCreatedAtDesc(String recipient, Instant now);

    /** All non-expired messages from a sender, ordered newest first */
    List<Message> findBySenderAndExpiresAtAfterOrderByCreatedAtDesc(String sender, Instant now);

    /** Bulk-delete every message whose expiry has passed */
    @Modifying
    @Query("DELETE FROM Message m WHERE m.expiresAt <= :now")
    int deleteAllExpired(Instant now);

    /** All non-expired messages between two users (both directions), oldest first */
    @Query("SELECT m FROM Message m WHERE m.expiresAt > :now AND " +
           "((m.sender = :a AND m.recipient = :b) OR (m.sender = :b AND m.recipient = :a)) " +
           "ORDER BY m.createdAt ASC")
    List<Message> findConversation(String a, String b, Instant now);

    /** Count how many expired messages exist (useful for monitoring) */
    @Query("SELECT COUNT(m) FROM Message m WHERE m.expiresAt <= :now")
    long countExpired(Instant now);
}
