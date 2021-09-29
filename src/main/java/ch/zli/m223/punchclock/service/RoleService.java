package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserService userService;

    public RoleService(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public List<Role> findAll(Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return roleRepository.findAll();
        } else throw new Exception("User is not admin!");
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public Role create(Role role, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return roleRepository.saveAndFlush(role);
        } else throw new Exception("User is not admin!");
    }

    public void delete(Long id, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            roleRepository.deleteById(id);
        } else throw new Exception("User is not admin!");
    }

    public Role update(Role role, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return roleRepository.save(role);
        } else throw new Exception("User is not admin!");
    }

}
