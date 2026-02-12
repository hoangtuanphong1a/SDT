package sdt.project.module.auth.repository;

import sdt.project.module.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByTokenAndRevokedAtIsNull(String token);

    @Modifying
    @Query("update RefreshToken r set r.revokedAt = CURRENT_TIMESTAMP where r.user.id = :userId")
    void revokeAllByUserId(UUID userId);
}
