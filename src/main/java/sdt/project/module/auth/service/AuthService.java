package sdt.project.module.auth.service;

import sdt.project.common.enums.RoleCode;
import sdt.project.common.exception.ApiException;
import sdt.project.common.exception.ConflictException;
import sdt.project.common.exception.NotFoundException;
import sdt.project.common.security.jwt.JwtService;
import sdt.project.common.utils.DateUtils;
import sdt.project.module.auth.dto.request.LoginRequest;
import sdt.project.module.auth.dto.request.RegisterCompanyRequest;
import sdt.project.module.auth.dto.request.RegisterJobSeekerRequest;
import sdt.project.module.auth.dto.response.AuthResponse;
import sdt.project.module.auth.dto.response.MeResponse;
import sdt.project.module.auth.entity.RefreshToken;
import sdt.project.module.company.dto.response.CompanyResponse;
import sdt.project.module.company.entity.Company;
import sdt.project.module.company.mapper.CompanyMapper;
import sdt.project.module.company.repository.CompanyRepository;
import sdt.project.module.jobseeker.dto.response.JobSeekerProfileResponse;
import sdt.project.module.jobseeker.entity.JobSeekerProfile;
import sdt.project.module.jobseeker.mapper.JobSeekerProfileMapper;
import sdt.project.module.jobseeker.repository.JobSeekerProfileRepository;
import sdt.project.module.user.entity.Permission;
import sdt.project.module.user.entity.Role;
import sdt.project.module.user.entity.User;
import sdt.project.module.user.entity.UserRole;
import sdt.project.module.user.repository.PermissionRepository;
import sdt.project.module.user.repository.RoleRepository;
import sdt.project.module.user.repository.UserRepository;
import sdt.project.module.user.repository.UserRoleRepository;
import sdt.project.module.user.service.PermissionService;
import sdt.project.module.user.service.RolePermissionService;
import sdt.project.module.user.service.UserRoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final UserRoleRepository userRoleRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final OtpService otpService;
    private final UserRoleService userRoleService;
    private final PermissionService permissionService;
    private final AuthenticationManager authenticationManager;
    private final JobSeekerProfileRepository jobSeekerProfileRepo;
    private final CompanyRepository companyRepo;
    private final JobSeekerProfileMapper jobSeekerProfileMapper;
    private final CompanyMapper companyMapper;

    public AuthService(
            UserRepository userRepo,
            RoleRepository roleRepo,
            PermissionRepository permissionRepo,
            UserRoleRepository userRoleRepo,
            PasswordEncoder encoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            OtpService otpService,
            UserRoleService userRoleService,
            PermissionService permissionService,
            AuthenticationManager authenticationManager,
            JobSeekerProfileRepository jobSeekerProfileRepo,
            CompanyRepository companyRepo,
            JobSeekerProfileMapper jobSeekerProfileMapper,
            CompanyMapper companyMapper
    ) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
        this.userRoleRepo = userRoleRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.otpService = otpService;
        this.userRoleService = userRoleService;
        this.permissionService = permissionService;
        this.authenticationManager = authenticationManager;
        this.jobSeekerProfileRepo = jobSeekerProfileRepo;
        this.companyRepo = companyRepo;
        this.jobSeekerProfileMapper = jobSeekerProfileMapper;
        this.companyMapper = companyMapper;
    }

    public void registerJobSeeker(RegisterJobSeekerRequest req) {

        if (userRepo.existsByEmail(req.email())) {
            throw new ConflictException("Email already exists");
        }

        User user = new User();
        user.setEmail(req.email());
        user.setPassword(encoder.encode(req.password()));
        user.setIsActive(true);
        userRepo.save(user);

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setUser(user);
        profile.setFullName(req.fullName());
        profile.setPhone(req.phone());
        jobSeekerProfileRepo.save(profile);

        Role role = roleRepo.findRoleByName(RoleCode.JOB_SEEKER.name());
        userRoleRepo.save(new UserRole(user, role, DateUtils.now()));

        otpService.sendOtp(user);
    }

    public void registerCompany(RegisterCompanyRequest req) {

        if (userRepo.existsByEmail(req.email())) {
            throw new ConflictException("Email already exists");
        }

        if (companyRepo.existsByTaxCode(req.taxCode())) {
            throw new ConflictException("Tax code already exists");
        }

        Company company = new Company();
        company.setName(req.companyName());
        company.setTaxCode(req.taxCode());
        company.setLocation(req.location());
        companyRepo.save(company);

        User user = new User();
        user.setEmail(req.email());
        user.setPassword(encoder.encode(req.password()));
        user.setCompany(company);
        user.setIsActive(true);
        user.setIsEmailConfirmed(false);
        userRepo.save(user);

        Role role = roleRepo.findRoleByName(RoleCode.COMPANY.name());
        userRoleRepo.save(new UserRole(user, role, DateUtils.now()));

        otpService.sendOtp(user);
    }

    public AuthResponse login(LoginRequest req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(), req.password()
                )
        );

        User user = userRepo.findByEmail(req.email()).orElseThrow();

        List<String> roles = userRoleService.getRolesByUserId(user.getId())
                .stream()
                .map(r -> "ROLE_" + r.getName())
                .toList();
        List<String> permissions =
                permissionService.getPermissionsByUserId(user.getId())
                        .stream()
                        .map(Permission::getName)
                        .toList();

        String accessToken =
                jwtService.generateToken(user, roles, permissions);

        String refreshToken =
                refreshTokenService.create(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {

        RefreshToken token =
                refreshTokenService.verify(refreshToken);

        User user = token.getUser();

        List<String> roles = userRoleService.getRolesByUserId(user.getId())
                .stream()
                .map(r -> "ROLE_" + r.getName())
                .toList();
        List<String> permissions =
                permissionService.getPermissionsByUserId(user.getId())
                        .stream()
                        .map(Permission::getName)
                        .toList();

        String newAccessToken =
                jwtService.generateToken(user, roles, permissions);

        refreshTokenService.revokeAll(user.getId());

        return new AuthResponse(
                newAccessToken,
                refreshTokenService.create(user)
        );
    }

    public void logout(UUID userId) {
        refreshTokenService.revokeAll(userId);
    }

    @Transactional(readOnly = true)
    public MeResponse getMe(UUID userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        String role = roleRepo.findPrimaryRoleByUser(userId)
                .orElseThrow(() ->
                        new IllegalStateException("User has no role"));

        JobSeekerProfileResponse jobSeeker = null;
        CompanyResponse company = null;

        switch (RoleCode.valueOf(role)) {
            case JOB_SEEKER -> {
                JobSeekerProfile profile =
                        jobSeekerProfileRepo.findByUserId(userId)
                                .orElseThrow(() ->
                                        new NotFoundException("JobSeeker profile not found"));

                jobSeeker = jobSeekerProfileMapper.toResponse(profile);
            }

            case COMPANY -> {
                Company entity =
                        companyRepo.findCompanyByUserId(userId)
                                .orElseThrow(() ->
                                        new NotFoundException("Company not found"));

                company = companyMapper.toResponse(entity);
            }

            default -> throw new IllegalStateException("Unsupported role: " + role);
        }

        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getIsEmailConfirmed(),
                role,
                jobSeeker,
                company
        );
    }
}

