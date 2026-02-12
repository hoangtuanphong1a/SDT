package sdt.project.module.user.service;

import sdt.project.module.user.entity.Permission;
import sdt.project.module.user.entity.Role;
import sdt.project.module.user.entity.RolePermission;
import sdt.project.module.user.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PermissionService {
    private final UserRoleService userRoleService;
    private final RolePermissionRepository rolePermissionRepository;

    public PermissionService(
            UserRoleService userRoleService,
            RolePermissionRepository rolePermissionRepository) {
        this.userRoleService = userRoleService;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public List<Permission> getPermissionsByUserId(UUID userId) {

        List<Role> roles =
                userRoleService.getRolesByUserId(userId);

        return roles.stream()
                .flatMap(role ->
                        rolePermissionRepository
                                .findByRole_Id(role.getId())
                                .stream()
                )
                .map(RolePermission::getPermission)
                .distinct()
                .toList();
    }
}
