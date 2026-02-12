package sdt.project.module.jobseeker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sdt.project.common.base.BaseEntity;
import sdt.project.module.user.entity.User;

import java.time.LocalDate;

@Entity
@Table(name = "JobSeekerProfiles")
@Getter
@Setter
public class JobSeekerProfile extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "UserId", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = true, length = 20)
    private String phone;

    private LocalDate DOB;
    private String location;
    private String summary;
    private String currentTitle;
    private Integer yearsExperience;
}
