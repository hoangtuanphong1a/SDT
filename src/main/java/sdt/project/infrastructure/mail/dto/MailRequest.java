package  sdt.project.infrastructure.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailRequest {

    @Email
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}