package sdt.project.module.company.dto.response;

import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        String taxCode,
        String website,
        String location,
        String description,
        Integer companySize
) {
}
