package com.msgDissapear.repository;

import com.msgDissapear.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByOwnerOrderByContactUsernameAsc(String owner);

    boolean existsByOwnerAndContactUsername(String owner, String contactUsername);
}
