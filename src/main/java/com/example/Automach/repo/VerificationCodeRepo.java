
package com.example.Automach.repo;

import com.example.Automach.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmail(String email);
    Optional<VerificationCode> findByCodeAndEmail(String code, String email);

    // Define deleteByEmail method to delete based on email
    void deleteByEmail(String email);
}
