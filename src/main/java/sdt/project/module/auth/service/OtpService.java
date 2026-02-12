package  sdt.project.module.auth.service;

import sdt.project.common.exception.ApiException;
import sdt.project.common.exception.ValidationException;
import sdt.project.common.utils.DateUtils;
import sdt.project.infrastructure.mail.MailService;
import sdt.project.infrastructure.mail.dto.MailRequest;
import sdt.project.module.auth.entity.OtpToken;
import sdt.project.module.auth.repository.OtpTokenRepository;
import sdt.project.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class OtpService {

    private final OtpTokenRepository otpRepo;
    private final MailService mailService;

    public OtpService(OtpTokenRepository otpRepo,
                      MailService mailService) {
        this.otpRepo = otpRepo;
        this.mailService = mailService;
    }

    public void sendOtp(User user) {

        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 999999));

        OtpToken token = new OtpToken();
        token.setUser(user);
        token.setOtp(otp);
        token.setExpiredAt(DateUtils.plusMinutes(5));
        otpRepo.save(token);

        mailService.send(new MailRequest(
                user.getEmail(),
                "Verify your account",
                "Your OTP is: " + otp
        ));
    }

    public void verify(String email, String otp) {

        OtpToken token = otpRepo
                .findValidOtp(email, otp)
                .orElseThrow(() ->
                        new ValidationException("OTP invalid or expired"));

        token.setUsed(true);
        token.getUser().setIsEmailConfirmed(true);
    }
}

