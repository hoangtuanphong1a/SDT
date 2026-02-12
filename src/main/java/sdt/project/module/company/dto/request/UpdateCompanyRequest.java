package sdt.project.module.company.dto.request;

import java.util.UUID;

public record UpdateCompanyRequest(
        UUID id,
        String companyName,
        String website,
        String location,
        String description,
        Integer companySize
) {
}
