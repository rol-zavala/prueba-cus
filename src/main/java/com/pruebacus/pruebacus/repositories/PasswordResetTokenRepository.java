package com.pruebacus.pruebacus.repositories;

import com.pruebacus.pruebacus.models.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String passwordResetToken);
}
