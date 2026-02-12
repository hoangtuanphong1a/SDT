package sdt.project.module.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterCompanyRequest(
        @Email String email,
        @NotBlank String password,
        @NotBlank String companyName,
        @NotBlank String taxCode,
        String location
) {}
