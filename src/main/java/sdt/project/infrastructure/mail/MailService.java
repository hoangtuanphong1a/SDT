package sdt.project.infrastructure.mail;

import  sdt.project.infrastructure.mail.dto.MailRequest;

public interface MailService {

    void send(MailRequest request);
}
