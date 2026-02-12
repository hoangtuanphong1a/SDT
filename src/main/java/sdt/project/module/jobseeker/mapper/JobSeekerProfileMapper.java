package sdt.project.module.jobseeker.mapper;

import sdt.project.module.jobseeker.dto.request.UpdateJobSeekerProfileRequest;
import sdt.project.module.jobseeker.dto.response.JobSeekerProfileResponse;
import sdt.project.module.jobseeker.entity.JobSeekerProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface  JobSeekerProfileMapper {
    JobSeekerProfileResponse toResponse(JobSeekerProfile entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(
            UpdateJobSeekerProfileRequest req,
            @MappingTarget JobSeekerProfile entity
    );
}
