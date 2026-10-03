package com.nexio.auth.repository;

import com.nexio.auth.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository
        extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByMobile(String mobile);

    boolean existsByMobile(String mobile);
}