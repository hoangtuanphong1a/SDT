package sdt.project.module.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest (
        @NotBlank
        String token,
        @NotBlank
        String newPassword
){}
