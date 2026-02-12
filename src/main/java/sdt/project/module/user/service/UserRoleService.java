package sdt.project.module.user.service;

import sdt.project.module.user.entity.Role;
import sdt.project.module.user.entity.UserRole;
import sdt.project.module.user.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public List<Role> getRolesByUserId(UUID userId) {
        return userRoleRepository.findRolesByUserId(userId);
    }
}
