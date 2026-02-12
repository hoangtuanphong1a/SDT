package sdt.project.module.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterJobSeekerRequest(
        @Email String email,
        @NotBlank String password,
        @NotBlank String fullName,
        @NotBlank String phone
) {}
