package sdt.project.infrastructure.config;

import sdt.project.module.company.entity.Company;
import sdt.project.module.company.repository.CompanyRepository;
import sdt.project.module.jobseeker.entity.JobSeekerProfile;
import sdt.project.module.jobseeker.repository.JobSeekerProfileRepository;
import sdt.project.module.user.entity.Permission;
import sdt.project.module.user.entity.Role;
import sdt.project.module.user.entity.User;
import sdt.project.module.user.entity.UserRole;
import sdt.project.module.user.repository.PermissionRepository;
import sdt.project.module.user.repository.RoleRepository;
import sdt.project.module.user.repository.UserRepository;
import sdt.project.module.user.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UserRepository userRepo,
            RoleRepository roleRepo,
            PermissionRepository permissionRepo,
            UserRoleRepository userRoleRepo,
            CompanyRepository companyRepo,
            JobSeekerProfileRepository jobSeekerProfileRepo,
            PasswordEncoder encoder
    ) {
        return args -> {
            // Only seed if database is empty
            if (userRepo.count() > 0) {
                System.out.println(">>> Data already exists, skipping seed...");
                return;
            }

            System.out.println(">>> Seeding initial data...");

            // Create Permissions
            Permission permReadJobs = createPermission(permissionRepo, "JOB_READ", "Read jobs");
            Permission permCreateJobs = createPermission(permissionRepo, "JOB_CREATE", "Create jobs");
            Permission permUpdateJobs = createPermission(permissionRepo, "JOB_UPDATE", "Update jobs");
            Permission permDeleteJobs = createPermission(permissionRepo, "JOB_DELETE", "Delete jobs");
            Permission permReadApplications = createPermission(permissionRepo, "APPLICATION_READ", "Read applications");
            Permission permCreateApplications = createPermission(permissionRepo, "APPLICATION_CREATE", "Create applications");
            Permission permUpdateApplications = createPermission(permissionRepo, "APPLICATION_UPDATE", "Update applications");
            Permission permDeleteApplications = createPermission(permissionRepo, "APPLICATION_DELETE", "Delete applications");
            Permission permManageUsers = createPermission(permissionRepo, "USER_MANAGE", "Manage users");
            Permission permManageCompanies = createPermission(permissionRepo, "COMPANY_MANAGE", "Manage companies");

            // Create Roles
            Role roleAdmin = createRole(roleRepo, "ADMIN", "System Administrator");
            Role roleCompany = createRole(roleRepo, "COMPANY", "Company Recruiter");
            Role roleJobSeeker = createRole(roleRepo, "JOB_SEEKER", "Job Seeker");

            // Create Admin User
            User admin = new User();
            admin.setEmail("admin@sdt.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setIsActive(true);
            admin.setIsEmailConfirmed(true);
            admin.setDisplayName("System Administrator");
            admin = userRepo.save(admin);

            // Assign ADMIN role
            UserRole adminRole = new UserRole();
            adminRole.setUser(admin);
            adminRole.setRole(roleAdmin);
            adminRole.setAssignedAt(LocalDateTime.now());
            userRoleRepo.save(adminRole);
            System.out.println(">>> Created admin user: admin@sdt.com / admin123");

            // Create Sample Company
            Company company = new Company();
            company.setName("Tech Solutions Inc.");
            company.setTaxCode("1234567890");
            company.setIndustry("Technology");
            company.setCompanySize("100-500");
            company.setWebsite("https://techsolutions.com");
            company.setLocation("Ho Chi Minh City, Vietnam");
            company.setDescription("A leading technology solutions provider");
            company.setIsVerified(true);
            company = companyRepo.save(company);

            // Create Company User
            User companyUser = new User();
            companyUser.setEmail("recruiter@techsolutions.com");
            companyUser.setPassword(encoder.encode("company123"));
            companyUser.setIsActive(true);
            companyUser.setIsEmailConfirmed(true);
            companyUser.setDisplayName("Tech Solutions Recruiter");
            companyUser.setCompany(company);
            companyUser = userRepo.save(companyUser);

            UserRole companyUserRole = new UserRole();
            companyUserRole.setUser(companyUser);
            companyUserRole.setRole(roleCompany);
            companyUserRole.setAssignedAt(LocalDateTime.now());
            userRoleRepo.save(companyUserRole);
            System.out.println(">>> Created company user: recruiter@techsolutions.com / company123");

            // Create Sample Job Seeker
            User jobSeeker = new User();
            jobSeeker.setEmail("john.doe@email.com");
            jobSeeker.setPassword(encoder.encode("seeker123"));
            jobSeeker.setIsActive(true);
            jobSeeker.setIsEmailConfirmed(true);
            jobSeeker.setDisplayName("John Doe");
            jobSeeker = userRepo.save(jobSeeker);

            JobSeekerProfile profile = new JobSeekerProfile();
            profile.setUser(jobSeeker);
            profile.setFullName("John Doe");
            profile.setPhone("+84901234567");
            profile.setLocation("Ho Chi Minh City, Vietnam");
            profile.setCurrentTitle("Senior Software Engineer");
            profile.setYearsExperience(5);
            profile.setSummary("Experienced software engineer with passion for building scalable applications");
            jobSeekerProfileRepo.save(profile);

            UserRole seekerRole = new UserRole();
            seekerRole.setUser(jobSeeker);
            seekerRole.setRole(roleJobSeeker);
            seekerRole.setAssignedAt(LocalDateTime.now());
            userRoleRepo.save(seekerRole);
            System.out.println(">>> Created job seeker: john.doe@email.com / seeker123");

            System.out.println(">>> Data seeding completed successfully!");
        };
    }

    private Permission createPermission(PermissionRepository repo, String name, String description) {
        return repo.findByName(name).orElseGet(() -> {
            Permission perm = new Permission(name, description);
            return repo.save(perm);
        });
    }

    private Role createRole(RoleRepository repo, String name, String description) {
        return repo.findRoleByName(name).orElseGet(() -> {
            Role role = new Role(name, description);
            return repo.save(role);
        });
    }
}