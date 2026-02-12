package sdt.project.module.user.entity;

import jakarta.persistence.*;
import sdt.project.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import sdt.project.module.company.entity.Company;
import sdt.project.module.jobseeker.entity.JobSeekerProfile;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean isEmailConfirmed = false;
    private Boolean isActive = true;
    private Boolean isDeleted = false;

    private String displayName;
    private String avatarUrl;
    private String preferredLocale;

    private String googleId;

    @OneToOne(mappedBy = "user")
    private JobSeekerProfile jobSeekerProfile;

    @ManyToOne
    @JoinColumn(name = "CompanyId")
    private Company company;

    public User() {
    }

    public User(String email, String password, Boolean isEmailConfirmed, Boolean isActive, Boolean isDeleted, String displayName, String avatarUrl, String preferredLocale, String googleId) {
        this.email = email;
        this.password = password;
        this.isEmailConfirmed = isEmailConfirmed;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.preferredLocale = preferredLocale;
        this.googleId = googleId;
    }
}
