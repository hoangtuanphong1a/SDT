package sdt.project.module.jobseeker.service;

import sdt.project.common.exception.NotFoundException;
import sdt.project.module.jobseeker.dto.request.UpdateJobSeekerProfileRequest;
import sdt.project.module.jobseeker.dto.response.JobSeekerProfileResponse;
import sdt.project.module.jobseeker.entity.JobSeekerProfile;
import sdt.project.module.jobseeker.mapper.JobSeekerProfileMapper;
import sdt.project.module.jobseeker.repository.JobSeekerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository repo;
    private final JobSeekerProfileMapper mapper;

    public JobSeekerProfileService(
            JobSeekerProfileRepository repo,
            JobSeekerProfileMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public JobSeekerProfileResponse getMyProfile(UUID userId) {

        JobSeekerProfile profile = repo.findByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException("Profile not found"));

        return mapper.toResponse(profile);
    }

    public void update(UUID userId, UpdateJobSeekerProfileRequest req) {

        JobSeekerProfile profile = repo.findByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException("Profile not found"));

        mapper.update(req, profile);
    }
}
