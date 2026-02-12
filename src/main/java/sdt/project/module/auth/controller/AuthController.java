package sdt.project.module.auth.controller;

import jakarta.validation.Valid;
import sdt.project.module.auth.dto.request.*;
import sdt.project.module.auth.dto.response.AuthResponse;
import sdt.project.common.response.ApiResponse;
import sdt.project.module.auth.dto.response.MeResponse;
import sdt.project.module.auth.service.AuthService;
import sdt.project.module.auth.service.OtpService;
import sdt.project.module.auth.service.PasswordResetService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;
    private final PasswordResetService passwordResetService;

    public AuthController(
            AuthService authService,
            OtpService otpService,
            PasswordResetService passwordResetService) {
        this.authService = authService;
        this.otpService = otpService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register-jobseeker")
    public ApiResponse<Void> registerJobSeeker(
            @Valid @RequestBody RegisterJobSeekerRequest req) {

        authService.registerJobSeeker(req);
        return ApiResponse.success(null);
    }

    @PostMapping("/register-company")
    public ApiResponse<Void> registerCompany(
            @Valid @RequestBody RegisterCompanyRequest req) {

        authService.registerCompany(req);
        return ApiResponse.success(null);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {

        return ApiResponse.success(authService.login(req));
    }

    @PostMapping("/verify-otp")
    public ApiResponse<Void> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest req) {

        otpService.verify(req.email(), req.otp());
        return ApiResponse.success(null);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication auth){
        authService.logout(UUID.fromString(auth.getName()));
        return ApiResponse.success(null);
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(
            @RequestBody RefreshTokenRequest req) {

        return ApiResponse.success(
                authService.refresh(req.refreshToken()));
    }

    @PostMapping("/request-reset-password")
    public ApiResponse<Void> requestResetPassword(
            @Valid @RequestBody RequestPasswordResetRequest req) {

        passwordResetService.request(req.email());
        return ApiResponse.success(null);
    }


    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest req) {

        passwordResetService.reset(req.token(), req.newPassword());
        return ApiResponse.success(null);
    }

    @GetMapping("/me")
    public ApiResponse<MeResponse> me(Authentication auth) {

        UUID userId = UUID.fromString(auth.getName());
        return ApiResponse.success(authService.getMe(userId));
    }
}
