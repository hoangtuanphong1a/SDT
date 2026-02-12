package sdt.project.module.auth.dto.response;

import sdt.project.module.company.dto.response.CompanyResponse;
import sdt.project.module.jobseeker.dto.response.JobSeekerProfileResponse;

import java.util.UUID;

public record MeResponse(
        UUID id,
        String email,
        boolean emailConfirmed,
        String role,
        JobSeekerProfileResponse jobSeeker,
        CompanyResponse company
) {}
