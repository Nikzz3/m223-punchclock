package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    public UserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public ApplicationUser getUserByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    public Boolean isAdmin(Principal principal) {
        Role role = getUserByUsername(principal.getName()).getRole();
        return role != null && Objects.equals(role.getName(), "Admin");
    }

    public List<ApplicationUser> findAdmins(Principal principal) throws Exception {
        if (isAdmin(principal)) {
            Role role = getUserByUsername(principal.getName()).getRole();
            return applicationUserRepository.findAllByRole(role);
        } else throw new Exception("User is not Admin!");
    }

    public ApplicationUser update(ApplicationUser user, Principal principal) throws Exception {
        if (isAdmin(principal)) {
            return applicationUserRepository.save(user);
        } else throw new Exception("User is not admin!");
    }

    public void delete(Long id, Principal principal) throws Exception {
        if (isAdmin(principal)) {
            applicationUserRepository.deleteById(id);
        } else throw new Exception("User is not admin!");
    }
}