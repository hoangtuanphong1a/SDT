package sdt.project.module.user.repository;

import sdt.project.module.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleByName(String name);
    @Query("""
    select r.name
    from UserRole ur
    join ur.role r
    where ur.user.id = :userId
""")
    Optional<String> findPrimaryRoleByUser(UUID userId);

}
