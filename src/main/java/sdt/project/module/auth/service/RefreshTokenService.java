package sdt.project.module.auth.service;

import sdt.project.common.exception.ApiException;
import sdt.project.common.exception.ValidationException;
import sdt.project.common.utils.DateUtils;
import sdt.project.module.auth.entity.RefreshToken;
import sdt.project.module.auth.repository.RefreshTokenRepository;
import sdt.project.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public String create(User user) {

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiredAt(DateUtils.plusDays(7));

        repo.save(token);
        return token.getToken();
    }

    public RefreshToken verify(String token) {
        RefreshToken rt = repo.findByTokenAndRevokedAtIsNull(token)
                .orElseThrow(() ->
                        new ValidationException("Invalid refresh token"));

        if (DateUtils.isExpired(rt.getExpiredAt())) {
            throw new ValidationException("Refresh token expired");
        }
        return rt;
    }

    public void revokeAll(UUID userId) {
        repo.revokeAllByUserId(userId);
    }
}
