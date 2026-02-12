package sdt.project.module.user.entity;

import sdt.project.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "UserRoles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"UserId","RoleId"})
)
@Getter
@Setter
public class UserRole extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;

    private LocalDateTime assignedAt;

    public UserRole() {
    }

    public UserRole(User user, Role role, LocalDateTime assignedAt) {
        this.user = user;
        this.role = role;
        this.assignedAt = assignedAt;
    }
}
