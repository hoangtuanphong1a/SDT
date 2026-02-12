package sdt.project.module.user.entity;

import sdt.project.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public Permission() {
    }

    public Permission(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
