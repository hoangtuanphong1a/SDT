package sdt.project.module.company.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sdt.project.common.base.BaseEntity;

@Entity
@Table(name = "Companies")
@Getter
@Setter
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String taxCode;

    private String slug;
    private String logoUrl;
    private String bannerUrl;
    private String industry;
    private String companySize;
    private String website;
    private String location;
    private String description;
    private Boolean isVerified = true;
}
