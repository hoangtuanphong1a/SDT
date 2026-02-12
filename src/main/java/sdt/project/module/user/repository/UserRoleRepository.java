package sdt.project.module.user.repository;

import sdt.project.module.user.entity.Role;
import sdt.project.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByUser_Id(UUID userId);

    @Query("""
        select ur.role
        from UserRole ur
        where ur.user.id = :userId
    """)
    List<Role> findRolesByUserId(UUID userId);

}
