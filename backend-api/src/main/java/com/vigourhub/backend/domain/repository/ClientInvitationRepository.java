package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.entity.account.ClientInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientInvitationRepository extends JpaRepository<ClientInvitation, UUID> {

    Optional<ClientInvitation> findByUsername(String username);
}
