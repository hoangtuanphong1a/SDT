package sdt.project.module.auth.repository;

import sdt.project.module.auth.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OtpTokenRepository extends JpaRepository<OtpToken, UUID> {
    @Query("""
        select o from OtpToken o
        where o.user.email = :email
          and o.otp = :otp
          and o.used = false
          and o.expiredAt > CURRENT_TIMESTAMP
    """)
    Optional<OtpToken> findValidOtp(String email, String otp);
}
