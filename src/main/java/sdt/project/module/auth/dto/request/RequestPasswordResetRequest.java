package sdt.project.module.auth.dto.request;

import jakarta.validation.constraints.Email;

public record RequestPasswordResetRequest (
        @Email String email
){}
