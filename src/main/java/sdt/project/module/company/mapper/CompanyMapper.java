package sdt.project.module.company.mapper;

import sdt.project.module.company.dto.request.UpdateCompanyRequest;
import sdt.project.module.company.dto.response.CompanyResponse;
import sdt.project.module.company.entity.Company;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    // Entity -> Response
    CompanyResponse toResponse(Company entity);

    // Update request -> Entity (ignore null fields)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(
            UpdateCompanyRequest req,
            @MappingTarget Company entity
    );
}
