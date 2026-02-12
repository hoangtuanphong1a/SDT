package sdt.project.module.auth.service;

import sdt.project.common.exception.ApiException;
import sdt.project.common.exception.NotFoundException;
import sdt.project.common.exception.UnauthorizedException;
import sdt.project.common.exception.ValidationException;
import sdt.project.common.utils.DateUtils;
import sdt.project.infrastructure.mail.MailService;
import sdt.project.infrastructure.mail.dto.MailRequest;
import sdt.project.module.auth.entity.PasswordResetToken;
import sdt.project.module.auth.repository.PasswordResetTokenRepository;
import sdt.project.module.user.entity.User;
import sdt.project.module.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetService {

    private final PasswordResetTokenRepository repo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public PasswordResetService(
            PasswordResetTokenRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder,
            MailService mailService
    ) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mailService = mailService;
    }

    public void request(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Email not found"));

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiredAt(DateUtils.plusMinutes(15));

        repo.save(token);

        mailService.send(new MailRequest(
                email,
                "Reset password",
                "Reset token: " + token.getToken()
        ));
    }

    public void reset(String token, String newPassword) {

        PasswordResetToken t = repo.findByTokenAndUsedFalse(token)
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid token"));

        if (DateUtils.isExpired(t.getExpiredAt())) {
            throw new ValidationException("Token expired");
        }

        User user = t.getUser();
        user.setPassword(encoder.encode(newPassword));

        t.setUsed(true);
    }
}
