package com.vigourhub.backend.repository;

import com.vigourhub.backend.domain.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
