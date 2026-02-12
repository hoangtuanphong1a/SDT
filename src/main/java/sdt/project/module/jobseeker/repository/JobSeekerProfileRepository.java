package sdt.project.module.jobseeker.repository;

import sdt.project.module.jobseeker.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, UUID> {
    Optional<JobSeekerProfile> findByUserId(UUID userId);
}
