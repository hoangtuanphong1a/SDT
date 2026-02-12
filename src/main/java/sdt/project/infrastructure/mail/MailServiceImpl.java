package sdt.project.infrastructure.mail;

import lombok.extern.slf4j.Slf4j;
import sdt.project.infrastructure.mail.dto.MailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(MailRequest request) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getContent());

            mailSender.send(message);

            log.info("Email sent to {}", request.getTo());

        } catch (Exception ex) {
            log.error("Failed to send email to {}", request.getTo(), ex);
            // Không throw exception để tránh ảnh hưởng flow auth
        }
    }
}
