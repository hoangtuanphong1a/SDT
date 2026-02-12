package sdt.project.module.user.entity;

import sdt.project.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "rolePermissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"RoleId","PermissionId"})
)
@Getter
@Setter
public class RolePermission extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "PermissionId", nullable = false)
    private Permission permission;

    private LocalDateTime assignedAt;

    public RolePermission() {
    }

    public RolePermission(Role role, Permission permission, LocalDateTime assignedAt) {
        this.role = role;
        this.permission = permission;
        this.assignedAt = assignedAt;
    }
}
