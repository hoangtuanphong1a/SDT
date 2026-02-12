package sdt.project.module.auth.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}