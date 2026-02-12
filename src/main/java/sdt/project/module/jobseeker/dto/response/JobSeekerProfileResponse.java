package sdt.project.module.jobseeker.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record JobSeekerProfileResponse(
        UUID id,
        String fullName,
        String phone,
        LocalDate DOB,
        String location,
        String summary,
        String currentTitle,
        Integer yearsExperience
) { }
